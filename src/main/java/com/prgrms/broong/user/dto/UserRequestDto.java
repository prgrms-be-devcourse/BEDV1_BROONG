package com.prgrms.broong.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequestDto {

    //    Long id;
    String email;
    String password;
    String name;
    boolean licenseInfo;
    boolean paymentMethod;
    Integer point;

}
