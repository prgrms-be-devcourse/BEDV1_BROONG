package com.prgrms.broong.management.car.dto;

import com.prgrms.broong.management.species.dto.SpeciesDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarRequestDto {

    private String carNum;

    private String model;

    private Long fuel;

    private Long price;

    private Integer possiblePassengers;

    private SpeciesDto speciesDto;

}
