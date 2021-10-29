package com.prgrms.broong.user.dto;

import com.prgrms.broong.reservation.domain.Reservation;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
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

    List<Reservation> reservations;
}
