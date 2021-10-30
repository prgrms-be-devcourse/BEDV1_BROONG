package com.prgrms.broong.management.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkCarResponseDto {

    private Long id;

    //임의로 ID값으로 넣었습니다.
    //나중에 CarDto, ParkDto 만드시면 바꿔주세요!
    private Long carId;

    private Long parkId;

}
