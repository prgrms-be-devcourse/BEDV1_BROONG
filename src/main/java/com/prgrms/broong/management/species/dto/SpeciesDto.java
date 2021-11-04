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

    @NotNull
    private Long id;

    @NotNull
    private String name;

}
