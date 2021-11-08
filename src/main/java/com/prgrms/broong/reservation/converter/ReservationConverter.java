package com.prgrms.broong.reservation.converter;

import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.domain.converter.ParkCarConverter;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.user.convert.UserConverter;
import com.prgrms.broong.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ParkCarConverter parkCarConverter;

    public Reservation ReservationToEntity(ReservationRequestDto addReservationRequest) {
        User user = userConverter.UserResponseToEntity(
            addReservationRequest.getUserResponseDto());
        ParkCar parkCar = parkCarConverter.parkCarResponseToEntity(
            addReservationRequest.getParkCarResponseDto());
        Reservation reservation = Reservation.builder()
            .reservationStatus(ReservationStatus.READY)
            .startTime(addReservationRequest.getStartTime())
            .endTime(addReservationRequest.getEndTime())
            .usagePoint(addReservationRequest.getUsagePoint())
            .fee(addReservationRequest.getFee())
            .parkCar(parkCar)
            .isOneway(addReservationRequest.getIsOneway())
            .user(user)
            .build();
        reservation.registerUser(user);
        return reservation;
    }

    public ReservationResponseDto ReservationToResponseDto(Reservation reservation) {
        ReservationResponseDto getReservation = ReservationResponseDto.builder()
            .id(reservation.getId())
            .reservationStatus(reservation.getReservationStatus())
            .startTime(reservation.getStartTime())
            .endTime(reservation.getEndTime())
            .usagePoint(reservation.getUsagePoint())
            .fee(reservation.getFee())
            .isOneway(reservation.getIsOneway())
            .parkCarResponseDto(
                parkCarConverter.parkCarToResponseDto(reservation.getParkCar()))
            .userResponseDto(
                userConverter.UserToResponseDtoWithoutReservationList(reservation.getUser()))
            .build();
        return getReservation;
    }
}