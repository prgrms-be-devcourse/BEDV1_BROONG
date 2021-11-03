package com.prgrms.broong.management.dto;

import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkCarRequestDto {

    ParkResponseDto parkResponseDto;

    CarResponseDto carResponseDto;

}
