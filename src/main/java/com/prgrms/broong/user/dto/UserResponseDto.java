package com.prgrms.broong.user.dto;

import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String locationName;

    private boolean licenseInfo;

    private boolean paymentMethod;

    private int point;

    private List<ReservationResponseDto> reservationResponseDto;

}
