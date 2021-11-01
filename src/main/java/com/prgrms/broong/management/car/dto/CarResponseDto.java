package com.prgrms.broong.management.car.dto;

import com.prgrms.broong.management.species.dto.SpeciesDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarResponseDto {

    private Long id;

    private String carNum;

    private String model;

    private Long fuel;

    private Long price;

    private Integer possiblePassengers;

    private SpeciesDto speciesDto;

}
