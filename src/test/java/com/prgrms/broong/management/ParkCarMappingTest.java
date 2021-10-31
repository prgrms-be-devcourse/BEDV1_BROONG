package com.prgrms.broong.management;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.repository.ParkCarRepository;
import com.prgrms.broong.management.species.domain.Species;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ParkCarMappingTest {

    @Autowired
    ParkCarRepository parkCarRepository;

    @DisplayName("Park와 Car의 연관관계 매핑 테스트")
    @Test
    void parCarMappingTest() {
        //given
        Species species = Species.builder()
            .name("중형")
            .build();

        Car car = Car.builder()
            .carNum("11허124333")
            .fuel(100L)
            .model("k5")
            .price(50000L)
            .possiblePassengers(10)
            .species(species)
            .build();

        Location location = Location.builder()
            .cityId("1")
            .townId("101")
            .locationName("도봉구")
            .build();

        Park park = Park.builder()
            .possibleNum(10)
            .location(location)
            .build();

        ParkCar parkCar = ParkCar.builder()
            .car(car)
            .park(park)
            .build();

        //when
        parkCarRepository.save(parkCar);

        //then
        assertThat(parkCar.getCar(), samePropertyValuesAs(car));
        assertThat(parkCar.getCar().getSpecies(), samePropertyValuesAs(car.getSpecies()));
        assertThat(parkCar.getPark(), samePropertyValuesAs(park));
        assertThat(parkCar.getPark().getLocation(), samePropertyValuesAs(park.getLocation()));
    }

}
