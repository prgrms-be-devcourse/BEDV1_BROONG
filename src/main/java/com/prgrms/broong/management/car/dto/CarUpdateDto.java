package com.prgrms.broong.management.car.dto;

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
public class CarUpdateDto {

    @NotNull
    private String carNum;

    @NotNull
    @Min(0)
    private Long fuel;

    @NotNull
    @Min(0)
    private Long price;

}
