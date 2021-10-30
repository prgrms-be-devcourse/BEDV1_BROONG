package com.prgrms.broong.reservation.dto;

import com.prgrms.broong.validation.TimeValid;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarReservationCheckDto {

    Long carId;

    @TimeValid
    LocalDateTime checkTime;

}
