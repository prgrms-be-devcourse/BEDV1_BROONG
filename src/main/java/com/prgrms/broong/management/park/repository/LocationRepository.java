package com.prgrms.broong.management.park.repository;

import com.prgrms.broong.management.park.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
