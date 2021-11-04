package com.prgrms.broong.management.car.dto;

import com.prgrms.broong.management.species.dto.SpeciesDto;
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
public class CarRequestDto {

    @NotNull(message = "carNum은 null일 수 없습니다.")
    private String carNum;

    @NotNull(message = "model null일 수 없습니다.")
    private String model;

    @NotNull(message = "fuel은 null일 수 없습니다.")
    @Min(value = 0, message = "fuel은 최소 0이어야 합니다.")
    private long fuel;

    @NotNull(message = "price는 null일 수 없습니다.")
    @Min(value = 0, message = "price는 최소 0이어야 합니다.")
    private long price;

    @NotNull(message = "possiblePassengers는 null일 수 없습니다.")
    @Min(value = 0, message = "possiblePassengers는 최소 0이어야 합니다.")
    private int possiblePassengers;

    @NotNull(message = "speciesDto는 null일 수 없습니다.")
    private SpeciesDto speciesDto;

}
