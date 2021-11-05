package com.prgrms.broong.management.car.dto;

import com.prgrms.broong.management.species.dto.SpeciesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarResponseDto {

    private Long id;

    private String carNum;

    private String model;

    private long fuel;

    private long price;

    private int possiblePassengers;

    private SpeciesDto speciesDto;

}
