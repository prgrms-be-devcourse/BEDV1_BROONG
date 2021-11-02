package com.prgrms.broong.reservation.service;

import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationService {

    Long saveReservation(ReservationRequestDto addReservationRequest,
        UserReservationCheckDto userReservationCheckDto);

    ReservationResponseDto getReservation(Long reservationId);

    Page<ReservationResponseDto> getReservationListByUserId(Long userId, Pageable pageable);

    boolean checkReservationByUserId(UserReservationCheckDto userReservationCheckDto,
        ReservationStatus reservationStatus);

    boolean possibleReservationTimeByCarId(Long carId, LocalDateTime checkTime,
        ReservationStatus reservationStatus);

    Long removeReservation(Long reservationId);

}