package com.prgrms.broong.reservation.converter;

import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {

    public Reservation ReservationToEntity(ReservationRequestDto addReservationRequest) {
        User user = null; //converterToEntity(addReservationRequest.user)
        Reservation reservation = Reservation.builder()
            .reservationStatus(ReservationStatus.RESERVATION)
            .startTime(addReservationRequest.getStartTime())
            .endTime(addReservationRequest.getEndTime())
            .usagePoint(addReservationRequest.getUsagePoint())
            .fee(addReservationRequest.getFee())
            .parkCar(null) //converterToEntity(addReservationRequest.parkCar)
            .isOneway(true)
            .user(user)
            .build();
        reservation.registerUser(user);
        return null;
    }

    public ReservationResponseDto ReservationToResponseDto(Reservation reservation) {
        ReservationResponseDto getReservation = ReservationResponseDto.builder()
            .id(reservation.getId())
            .reservationStatus(reservation.getReservationStatus())
            .startTime(reservation.getStartTime())
            .endTime(reservation.getEndTime())
            .usagePoint(reservation.getUsagePoint())
            .fee(reservation.getFee())
            .isOneway(reservation.isOneway())
            .build();
        return getReservation;
    }

}
