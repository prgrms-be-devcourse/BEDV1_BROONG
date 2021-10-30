package com.prgrms.broong.user.dto;

import com.prgrms.broong.validation.TimeValid;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReservationCheckDto {

    Long id;

    @TimeValid
    LocalDateTime checkTime;

}
