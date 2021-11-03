package com.prgrms.broong.management.species.service;

import com.prgrms.broong.management.species.dto.SpeciesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpeciesService {

    Page<SpeciesDto> findAll(Pageable pageable);

}
