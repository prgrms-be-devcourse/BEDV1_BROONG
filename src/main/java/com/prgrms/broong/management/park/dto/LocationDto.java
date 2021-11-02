package com.prgrms.broong.management.park.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationDto {

    private Long id;

    private String cityId;

    private String townId;

    private String locationName;

}
