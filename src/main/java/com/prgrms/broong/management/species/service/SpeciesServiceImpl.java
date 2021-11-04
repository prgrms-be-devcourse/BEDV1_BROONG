package com.prgrms.broong.management.species.service;

import com.prgrms.broong.management.species.converter.SpeciesConverter;
import com.prgrms.broong.management.species.dto.SpeciesDto;
import com.prgrms.broong.management.species.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpeciesServiceImpl implements SpeciesService {

    private final SpeciesRepository speciesRepository;

    private final SpeciesConverter speciesConverter;

    @Override
    public Page<SpeciesDto> findAll(Pageable pageable) {
        return speciesRepository.findAll(pageable)
            .map(speciesConverter::speciesToDto);
    }

}
