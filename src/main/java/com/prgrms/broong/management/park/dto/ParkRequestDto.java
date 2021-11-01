package com.prgrms.broong.management.park.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkRequestDto {

    private Integer possibleNum;

    private LocationDto locationDto;

}
