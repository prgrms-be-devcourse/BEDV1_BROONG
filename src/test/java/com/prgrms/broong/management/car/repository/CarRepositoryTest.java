package com.prgrms.broong.management.car.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.dto.CarUpdateDto;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.repository.SpeciesRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;

    @Autowired
    SpeciesRepository speciesRepository;

    private Car car;

    @BeforeEach
    void setup() {
        Species species = Species.builder()
            .name("중형")
            .build();
        speciesRepository.save(species);

        car = Car.builder()
            .carNum("11허124333")
            .fuel(100L)
            .model("k5")
            .price(50000L)
            .possiblePassengers(10)
            .species(species)
            .build();
    }

    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
        speciesRepository.deleteAll();
    }

    @DisplayName("차량 생성 테스트")
    @Test
    void saveCarTest() {
        //when
        carRepository.save(car);

        //then
        List<Car> cars = carRepository.findAll();

        assertThat(cars).hasSize(1);
    }

    @DisplayName("차량 조회 테스트")
    @Test
    void getCarTest() {
        //when
        carRepository.save(car);

        //then
        Car getCar = carRepository.findById(car.getId()).get();

        assertThat(getCar, samePropertyValuesAs(car));
    }

    @Transactional
    @DisplayName("차량 변경 테스트")
    @Test
    void editCarTest() {
        //given
        carRepository.save(car);

        //when
        car.changeCarInfo(
            CarUpdateDto.builder()
                .carNum("11호123456")
                .fuel(50L)
                .price(3000L)
                .build()
        );

        //then
        Car updatedCar = carRepository.findById(car.getId()).get();

        assertThat(updatedCar.getCarNum()).isEqualTo(car.getCarNum());
    }

    @DisplayName("차량 삭제 테스트")
    @Test
    void removeCarTest() {
        //given
        carRepository.save(car);

        //when
        carRepository.deleteAll();

        //then
        List<Car> cars = carRepository.findAll();

        assertThat(cars).isEmpty();
    }
}