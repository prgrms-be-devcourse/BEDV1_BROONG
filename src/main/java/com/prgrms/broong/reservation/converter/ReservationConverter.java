package com.prgrms.broong.reservation.converter;

import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {

    public Reservation ReservationToEntity(ReservationRequestDto addReservationRequest) {
        User user = null; //converterToEntity(addReservationRequest.user)
        Reservation reservation = Reservation.builder()
            .reservationStatus(addReservationRequest.getReservationStatus())
            .startTime(addReservationRequest.getStartTime())
            .endTime(addReservationRequest.getEndTime())
            .usagePoint(addReservationRequest.getUsagePoint())
            .fee(addReservationRequest.getFee())
            .parkCar(null) //converterToEntity(addReservationRequest.parkCar)
            .isOneway(true)
            .user(user)
            .build();
        reservation.setUser(user);
        return null;
    }

    public ReservationResponseDto ReservationToDto(Reservation reservation) {
        ReservationResponseDto getReservation = ReservationResponseDto.builder()
            .id(reservation.getId())
            .reservationStatus(reservation.getReservationStatus()) //DB에서 꺼내면 STRING으로 되나? 확인
            .startTime(reservation.getStartTime()) //날짜 형식 맞는지 확인하기 -> String으로 바꿔줘야 함
            .endTime(reservation.getEndTime())  //날짜 형식 맞는지 확인하기
            .usagePoint(reservation.getUsagePoint())
            .fee(reservation.getFee())
            .isOneway(reservation.isOneway())
            .build();
        return getReservation;
    }

}
