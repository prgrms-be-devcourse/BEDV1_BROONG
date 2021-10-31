package com.prgrms.broong.management.car.dto;

import com.prgrms.broong.management.species.domain.Species;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarResponseDto {

    Long id;

    String carNum;

    String model;

    Long fuel;

    Long price;

    Integer possiblePassengers;

    Species species;

}
