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

    @NotNull(message = "id는 null일 수 없습니다.")
    private Long id;

    @NotNull(message = "cityId는 null일 수 없습니다.")
    private String cityId;

    @NotNull(message = "townId는 null일 수 없습니다.")
    private String townId;

    @NotNull(message = "locationName은 null일 수 없습니다.")
    private String locationName;

}
