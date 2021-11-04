package com.prgrms.broong.management.car.dto;

import com.prgrms.broong.management.species.dto.SpeciesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarResponseDto {

    private Long id;

    private String carNum;

    private String model;

    private Long fuel;

    private Long price;

    private Integer possiblePassengers;

    private SpeciesDto speciesDto;

}
