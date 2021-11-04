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

    @NotNull(message = "null값 입니다.")
    @Min(message = "0이상이여야 합니다", value = 0)
    private Integer possibleNum;

    private LocationDto locationDto;

}
