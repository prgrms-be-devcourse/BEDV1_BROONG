package com.prgrms.broong.reservation.dto;


import com.prgrms.broong.reservation.domain.ReservationStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponseDto {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer usagePoint;

    private ReservationStatus reservationStatus;

    private boolean isOneway;

    private Integer fee;

//    private ParkCarResponse parkCarResponse;

}
