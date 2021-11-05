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

    private Long id;

    @TimeValid
    private LocalDateTime checkStartTime;

    @TimeValid
    private LocalDateTime checkEndTime;

}
