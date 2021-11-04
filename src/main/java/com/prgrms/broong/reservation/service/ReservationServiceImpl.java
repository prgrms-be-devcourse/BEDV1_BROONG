package com.prgrms.broong.reservation.service;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.repository.CarRepository;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Long ADD_HOUR = 2L;

    private final ReservationRepository repository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ReservationConverter converter;

    @Transactional
    @Override
    public Long saveReservation(ReservationRequestDto addReservationRequest) {
        Reservation reservation = converter.ReservationToEntity(addReservationRequest);
        Car car = carRepository.findById(
            addReservationRequest.getParkCarResponseDto().getCarResponseDto().getId()).get();
        User getUser = userRepository.findById(addReservationRequest.getUserResponseDto().getId())
            .get();

        checkReservationByUserId(UserReservationCheckDto.builder().id(getUser.getId())
            .checkTime(addReservationRequest.getStartTime()).build(), ReservationStatus.CANCELD);

        possibleReservationTimeByCarId(car.getId(), addReservationRequest.getStartTime(),
            ReservationStatus.CANCELD);

        if (!getUser.getLicenseInfo()) {
            throw new RuntimeException(
                MessageFormat.format("사용자:{0}는 면허를 등록해 주세요",
                    getUser.getId()));
        }
        if (!getUser.getPaymentMethod()) {
            throw new RuntimeException(
                MessageFormat.format("사용자:{0}는 결제수단을 등록해 주세요",
                    getUser.getId()));
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
    public Page<ReservationResponseDto> getReservationListByUserId(Long userId, Pageable pageable) {
        return repository.findReservationsByUserId(userId, pageable)
            .map(converter::ReservationToResponseDto);
    }

    @Override
    public boolean checkReservationByUserId(UserReservationCheckDto userReservationCheckDto,
        ReservationStatus reservationStatus) {
        long reservationCount = repository.checkReservationByUserId(userReservationCheckDto.getId(),
            userReservationCheckDto.getCheckTime(), reservationStatus).stream().count();
        if (reservationCount != 0) {
            throw new RuntimeException(
                MessageFormat.format("사용자:{0}키는 동일한 시간대에 예약이 존재합니다.",
                    userReservationCheckDto.getId()));
        }
        return true;
    }

    @Override
    public boolean possibleReservationTimeByCarId(Long carId, LocalDateTime checkTime,
        ReservationStatus reservationStatus) {
        long reservationCount = repository.possibleReservationTimeByCarId(carId,
            checkTime, reservationStatus).stream().count();
        if (reservationCount != 0) {
            throw new RuntimeException(
                MessageFormat.format("자동차:{0}키는 동일한 시간대에 예약이 존재합니다.", carId));
        }
        return true;
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