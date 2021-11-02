package com.prgrms.broong.management.car.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarUpdateDto {

    private String carNum;

    private Long fuel;

    private Long price;

}
