package com.prgrms.broong.management.species.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDto {

    @NotNull(message = "id는 null일 수 없습니다.")
    private Long id;

    @NotNull(message = "차종은 null일 수 없습니다.")
    private String name;

}
