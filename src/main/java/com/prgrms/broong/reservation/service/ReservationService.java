package com.prgrms.broong.reservation.service;

import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;

public interface ReservationService {

    Long saveReservation(ReservationRequestDto reservationRequestDto);

    ReservationResponseDto getReservation(Long reservationId);

}
