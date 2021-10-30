package com.prgrms.broong.management.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.repository.LocationRepository;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.repository.ParkCarRepository;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.repository.SpeciesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ParkCarMappingTest {

    @Autowired
    CarRepository carRepository;

    @Autowired
    SpeciesRepository speciesRepository;

    @Autowired
    ParkRepository parkRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ParkCarRepository parkCarRepository;

    @DisplayName("Park와 Car의 연관관계 매핑 테스트")
    @Test
    void parCarMappingTest() {
        //given
        Species species = Species.builder()
            .name("중형")
            .build();
        speciesRepository.save(species);

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
        locationRepository.save(location);

        Park park = Park.builder()
            .possibleNum(10)
            .location(location)
            .build();

        ParkCar parkCar = ParkCar.builder().build();

        parkCar.registerCar(car);
        parkCar.registerPark(park);

        //when
        parkCarRepository.save(parkCar);

        //then
        assertThat(parkCar.getCar(), samePropertyValuesAs(car));
        assertThat(parkCar, samePropertyValuesAs(park.getParkCars().get(0)));
        assertThat(parkCar.getPark(), samePropertyValuesAs(park));
    }

}
