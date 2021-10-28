package com.prgrms.broong.management.species.repository;

import com.prgrms.broong.management.species.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeciesRepository extends JpaRepository<Species, Long> {

}
