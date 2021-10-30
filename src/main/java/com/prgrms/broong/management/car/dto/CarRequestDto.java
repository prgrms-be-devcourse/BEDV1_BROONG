package com.prgrms.broong.management.car.dto;

import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.species.domain.Species;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarRequestDto {

    String carNum;

    String model;

    Long fuel;

    Long price;

    Integer possiblePassengers;

    Species species;

    ParkCar parkCar;

}
