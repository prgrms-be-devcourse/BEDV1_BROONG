package com.prgrms.broong.user.convert;

import com.prgrms.broong.reservation.converter.ReservationConverter;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    ReservationConverter reservationConverter;

    public User UserToEntity(UserRequestDto userRequestDto) {
        return User.builder()
            .email(userRequestDto.getEmail())
            .name(userRequestDto.getName())
            .password(userRequestDto.getPassword())
            .locationName(userRequestDto.getLocationName())
            .licenseInfo(userRequestDto.isLicenseInfo())
            .paymentMethod(userRequestDto.isPaymentMethod())
            .build();
    }

    public UserResponseDto UserToResponseDto(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .name(user.getName())
            .locationName(user.getLocationName())
            .point(user.getPoint())
            .paymentMethod(user.isPaymentMethod())
            .reservationResponseDto(
                user.getReservations().stream().map(reservationConverter::ReservationToResponseDto)
                    .collect(Collectors.toList()))
            .licenseInfo(user.isLicenseInfo())
            .build();
    }

    public UserResponseDto UserToResponseDtoWithoutReservationList(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .name(user.getName())
            .locationName(user.getLocationName())
            .point(user.getPoint())
            .paymentMethod(user.isPaymentMethod())
            .licenseInfo(user.isLicenseInfo())
            .build();
    }


    public User UserResponseToEntity(UserResponseDto userResponseDto) {
        return User.builder()
            .id(userResponseDto.getId())
            .email(userResponseDto.getEmail())
            .name(userResponseDto.getName())
            .password(userResponseDto.getPassword())
            .locationName(userResponseDto.getLocationName())
            .point(userResponseDto.getPoint())
            .licenseInfo(userResponseDto.isLicenseInfo())
            .paymentMethod(userResponseDto.isPaymentMethod())
            .build();
    }

}
