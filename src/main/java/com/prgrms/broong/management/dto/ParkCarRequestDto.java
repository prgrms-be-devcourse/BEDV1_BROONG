package com.prgrms.broong.management.dto;

import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkCarRequestDto {

    @NotNull(message = "parkResponseDto는 null일 수 없습니다.")
    private ParkResponseDto parkResponseDto;

    @NotNull(message = "CarResponseDto는 null일 수 없습니다.")
    private CarResponseDto carResponseDto;

}
