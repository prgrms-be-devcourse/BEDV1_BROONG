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

    @NotNull
    private String carNum;

    @NotNull
    private String model;

    @NotNull
    @Min(0)
    private Long fuel;

    @NotNull
    @Min(0)
    private Long price;

    @NotNull
    @Min(0)
    private Integer possiblePassengers;

    private SpeciesDto speciesDto;

}
