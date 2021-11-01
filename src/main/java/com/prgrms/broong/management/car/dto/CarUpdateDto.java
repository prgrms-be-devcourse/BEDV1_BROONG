package com.prgrms.broong.management.car.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarUpdateDto {

    Long id;

    String carNum;

    String model;

    Long fuel;

    Long price;

    Integer possiblePassengers;

}
