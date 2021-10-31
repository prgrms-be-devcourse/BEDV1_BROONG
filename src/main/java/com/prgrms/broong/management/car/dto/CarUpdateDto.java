package com.prgrms.broong.management.car.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarUpdateDto {

    String carNum;

    Long fuel;

    Long price;

}
