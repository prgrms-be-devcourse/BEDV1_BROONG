package com.prgrms.broong.management.park.dto;

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
public class ParkRequestDto {

    @NotNull(message = "possibleNum은 null일 수 없습니다.")
    @Min(value = 0, message = "possibleNum은 0이상이어야 합니다.")
    private int possibleNum;

    @NotNull(message = "locationDto는 null일 수 없습니다.")
    private LocationDto locationDto;

}
