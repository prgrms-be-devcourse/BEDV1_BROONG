package com.prgrms.broong.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Boolean licenseInfo;

    private Boolean paymentMethod;

    private int point;

    @JsonIgnore
    private List<ReservationResponseDto> reservationResponseDto;

}
