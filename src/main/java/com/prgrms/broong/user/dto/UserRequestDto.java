package com.prgrms.broong.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequestDto {

    String email;

    String password;

    String name;

    boolean licenseInfo;

    boolean paymentMethod;

    Integer point;

}
