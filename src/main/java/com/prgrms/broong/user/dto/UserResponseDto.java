package com.prgrms.broong.user.dto;

import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserResponseDto {

    Long id;

    String email;

    String password;

    String name;

    String locationName;

    boolean licenseInfo;

    boolean paymentMethod;

    Integer point;

    List<ReservationResponseDto> reservationResponseDto;
  
}
