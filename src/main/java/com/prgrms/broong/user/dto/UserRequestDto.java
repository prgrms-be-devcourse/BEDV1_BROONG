package com.prgrms.broong.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    String email;

    String password;

    String name;

    String locationName;

    boolean licenseInfo;

    boolean paymentMethod;

    Integer point;

}
