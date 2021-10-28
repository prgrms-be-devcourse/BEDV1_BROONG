package com.prgrms.broong.management.park.repository;

import com.prgrms.broong.management.park.domain.Park;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRepository extends JpaRepository<Park, Long> {

}
