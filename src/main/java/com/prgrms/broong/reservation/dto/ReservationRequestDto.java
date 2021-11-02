package com.prgrms.broong.reservation.dto;

import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.user.dto.UserResponseDto;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationRequestDto {

    @NotNull(message = "startTime is not NULL")
    private LocalDateTime startTime;

    @NotNull(message = "endTime is not NULL")
    private LocalDateTime endTime;

    private Integer usagePoint;

    private ReservationStatus reservationStatus;

    @NotNull(message = "fee is not NULL")
    private Integer fee;

    private boolean isOneway;

    private UserResponseDto userResponseDto;

    private ParkCarResponseDto parkCarResponseDto;

}