package com.prgrms.broong.user.dto;

import com.prgrms.broong.validation.TimeValid;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReservationCheckDto {

    Long id;

    @TimeValid
    LocalDateTime checkTime;

}
