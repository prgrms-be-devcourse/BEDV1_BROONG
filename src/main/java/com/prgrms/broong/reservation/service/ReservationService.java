package com.prgrms.broong.reservation.service;

import com.prgrms.broong.reservation.dto.CarReservationCheckDto;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import org.springframework.data.domain.Page;

public interface ReservationService {

    Long saveReservation(ReservationRequestDto reservationRequestDto);

    ReservationResponseDto getReservation(Long reservationId);

    Page<ReservationResponseDto> getReservationListByUserId(Long userId);

    boolean checkReservationByUserId(UserReservationCheckDto userReservationCheckDto);

    boolean possibleReservationTimeByCarId(CarReservationCheckDto carReservationCheckDto);

    Long removeReservation(Long reservationId);

}
