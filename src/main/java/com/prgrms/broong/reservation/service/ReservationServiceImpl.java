package com.prgrms.broong.reservation.service;

import com.prgrms.broong.reservation.converter.ReservationConverter;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.CarReservationCheckDto;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.reservation.repository.ReservationRepository;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import java.text.MessageFormat;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;

    private final ReservationConverter converter;

    @Transactional
    @Override
    public Long saveReservation(ReservationRequestDto addReservationRequest) {
        Reservation reservation = converter.ReservationToEntity(addReservationRequest);
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
    public boolean checkReservationByUserId(UserReservationCheckDto userReservationCheckDto) {
        Optional<Reservation> checkReservation =
            repository.checkReservationByUserId(userReservationCheckDto.getId(),
                userReservationCheckDto.getCheckTime());
        return checkReservation.isPresent();
    }

    @Override
    public boolean possibleReservationTimeByCarId(CarReservationCheckDto carReservationCheckDto) {
        Optional<Reservation> checkReservation =
            repository.possibleReservationTimeByCarId(carReservationCheckDto.getCarId(),
                carReservationCheckDto.getCheckTime());
        return checkReservation.isPresent();
    }

    @Transactional
    @Override
    public Long removeReservation(Long reservationId) {
        Reservation reservation = repository.findById(reservationId)
            .orElseThrow(() -> new RuntimeException(
                MessageFormat.format("해당 {0}키의 객체를 찾을 수 없습니다.", reservationId)));
        reservation.changeReservationStatus(ReservationStatus.CANCELD);
        return reservationId;
    }
}
