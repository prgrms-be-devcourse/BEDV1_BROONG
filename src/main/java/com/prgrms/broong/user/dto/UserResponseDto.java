package com.prgrms.broong.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    Long id;

    String email;

    String password;

    String name;

    boolean licenseInfo;

    boolean paymentMethod;

    Integer point;
    //예약 dto에 대한 필드가 들어야함
}
