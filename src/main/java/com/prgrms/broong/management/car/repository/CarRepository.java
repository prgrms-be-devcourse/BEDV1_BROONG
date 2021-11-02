package com.prgrms.broong.management.car.repository;

import com.prgrms.broong.management.car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
