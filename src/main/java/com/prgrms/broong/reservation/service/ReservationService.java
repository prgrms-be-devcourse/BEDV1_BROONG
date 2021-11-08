package com.prgrms.broong.reservation.service;

import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;

public interface ReservationService {

    Long saveReservation(ReservationRequestDto addReservationRequest);

    ReservationResponseDto getReservation(Long reservationId);

    Page<ReservationResponseDto> getReservationListByUserId(Long userId, int pageNum, int pageSize);

    boolean checkReservationByUserId(UserReservationCheckDto userReservationCheckDto);

    boolean possibleReservationTimeByCarId(Long carId, LocalDateTime checkStartTime,
        LocalDateTime checkEndTime);

    Long removeReservation(Long reservationId);

    void editReservationByReservationQueue(Long reservationId, ReservationStatus changeStatusValue);

}