package com.prgrms.broong.reservation.dto;

import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.validation.TimeValid;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDto {

    @TimeValid
    @NotNull(message = "startTime is not NULL")
    private LocalDateTime startTime;

    @NotNull(message = "endTime is not NULL")
    private LocalDateTime endTime;

    @Min(0)
    private Integer usagePoint;

    private ReservationStatus reservationStatus;

    @Min(0)
    @NotNull(message = "fee is not NULL")
    private Integer fee;

    @NotNull(message = "isOneway is not NULL")
    private Boolean isOneway;

    private UserResponseDto userResponseDto;

    private ParkCarResponseDto parkCarResponseDto;

}