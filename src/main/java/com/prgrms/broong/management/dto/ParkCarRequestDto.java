package com.prgrms.broong.management.dto;

import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkCarRequestDto {

    private Long id;

    private CarResponseDto carResponseDto;

    private ParkResponseDto parkResponseDto;
}

