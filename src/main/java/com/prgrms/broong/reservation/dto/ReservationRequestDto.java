package com.prgrms.broong.reservation.dto;

import com.prgrms.broong.reservation.domain.ReservationStatus;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

//    @NotNull(message = "ParkCar is not NULL")
//    private ParkCarResponse parkCarResponse;

}
