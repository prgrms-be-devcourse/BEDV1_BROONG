package com.prgrms.broong.management.species.converter;

import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.dto.SpeciesDto;
import org.springframework.stereotype.Component;

@Component
public class SpeciesConverter {

    public Species speciesToEntity(SpeciesDto speciesDto) {
        return Species.builder()
            .id(speciesDto.getId())
            .name(speciesDto.getName())
            .build();
    }

    public SpeciesDto speciesToDto(Species species) {
        return SpeciesDto.builder()
            .id(species.getId())
            .name(species.getName())
            .build();
    }

}
