package com.prgrms.broong.reservation.service;

import com.prgrms.broong.reservation.converter.ReservationConverter;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.reservation.repository.ReservationRepository;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import com.prgrms.broong.user.repository.UserRepository;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Long ADD_HOUR = 2L;
    private final ReservationRepository repository;
    private final UserRepository userRepository;
    private final ReservationConverter converter;

    @Transactional
    @Override
    public Long saveReservation(ReservationRequestDto addReservationRequest,
        UserReservationCheckDto userReservationCheckDto) {
        Reservation reservation = converter.ReservationToEntity(addReservationRequest);
        User getUser = userRepository.findById(addReservationRequest.getUserResponseDto().getId())
            .get();
        boolean checkUserReservation = checkReservationByUserId(userReservationCheckDto,
            addReservationRequest.getReservationStatus());
        if (checkUserReservation) {
            return 0L;
        }
        boolean possibleReservation = possibleReservationTimeByCarId(
            reservation.getParkCar().getCar().getId(),
            userReservationCheckDto.getCheckTime(),
            addReservationRequest.getReservationStatus());
        if (possibleReservation) {
            return 0L;
        }
        if (!getUser.isLicenseInfo() || !getUser.isPaymentMethod()) {
            return 0L;
        }
        if (!reservation.isOneway()) {
            reservation.changeEndTime(ADD_HOUR);
        }
        getUser.reduceUsagePoint(reservation.getUsagePoint());
        return repository.save(reservation).getId();
    }

    @Override
    public ReservationResponseDto getReservation(Long reservationId) {
        return repository.findById(reservationId)
            .map(converter::ReservationToResponseDto)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 객체를 찾을 수 없습니다.", reservationId)));
    }

    @Override
    public Page<ReservationResponseDto> getReservationListByUserId(Long userId) {
        return repository.findReservationsByUserId(userId)
            .map(converter::ReservationToResponseDto);
    }

    @Override
    public boolean checkReservationByUserId(UserReservationCheckDto userReservationCheckDto,
        ReservationStatus reservationStatus) {
        Optional<Reservation> checkReservation =
            repository.checkReservationByUserId(userReservationCheckDto.getId(),
                userReservationCheckDto.getCheckTime(), reservationStatus);
        return checkReservation.isPresent();
    }

    @Override
    public boolean possibleReservationTimeByCarId(Long carId, LocalDateTime checkTime,
        ReservationStatus reservationStatus) {
        Optional<Reservation> checkReservation =
            repository.possibleReservationTimeByCarId(carId,
                checkTime, reservationStatus);
        return checkReservation.isPresent();
    }

    @Transactional
    @Override
    public Long removeReservation(Long reservationId) {
        Reservation reservation = repository.findReservationAndUser(reservationId)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 객체를 찾을 수 없습니다.", reservationId)));
        reservation.changeReservationStatus(ReservationStatus.CANCELD);
        Integer returnUsagePoint = reservation.getUsagePoint() + reservation.getUser().getPoint();
        reservation.getUser().changePoint(returnUsagePoint);
        return reservationId;
    }
}
