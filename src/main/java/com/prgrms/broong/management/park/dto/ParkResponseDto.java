package com.prgrms.broong.management.park.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkResponseDto {

    private Long id;

    private int possibleNum;

    private LocationDto locationDto;

}
