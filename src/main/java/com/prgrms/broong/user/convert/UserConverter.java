package com.prgrms.broong.user.convert;

import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User UserToEntity(UserRequestDto userRequestDto) {
        return User.builder()
            .email(userRequestDto.getEmail())
            .name(userRequestDto.getName())
            .password(userRequestDto.getPassword())
            .point(userRequestDto.getPoint())
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
            .point(user.getPoint())
            .paymentMethod(user.isPaymentMethod())
            .licenseInfo(user.isLicenseInfo())
            .build();
    }

}