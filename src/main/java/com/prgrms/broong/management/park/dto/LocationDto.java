package com.prgrms.broong.management.park.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    @NotNull
    private Long id;

    @NotNull
    private String cityId;

    @NotNull
    private String townId;

    @NotNull
    private String locationName;

}
