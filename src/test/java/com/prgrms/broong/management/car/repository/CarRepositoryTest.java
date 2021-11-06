package com.prgrms.broong.management.car.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.dto.CarUpdateDto;
import com.prgrms.broong.management.species.domain.Species;
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

    private Car car;

    @BeforeEach
    void setup() {
        Species species = Species.builder()
            .name("중형")
            .build();

        car = Car.builder()
            .carNum("11허124333")
            .fuel(100L)
            .model("k5")
            .price(50000L)
            .possiblePassengers(10)
            .species(species)
            .build();

        carRepository.save(car);
    }

    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
    }

    @DisplayName("차량 생성 테스트")
    @Test
    void saveCarTest() {
        //then
        List<Car> cars = carRepository.findAll();

        assertThat(cars).hasSize(1);
    }

    @DisplayName("차량 조회 테스트")
    @Test
    void getCarTest() {
        //then
        Car getCar = carRepository.findById(car.getId()).get();

        assertThat(getCar.getCarNum()).isEqualTo(car.getCarNum());
        assertThat(getCar.getModel()).isEqualTo(car.getModel());
        assertThat(getCar.getPrice()).isEqualTo(car.getPrice());
        assertThat(getCar.getFuel()).isEqualTo(car.getFuel());
        assertThat(getCar.getPossiblePassengers()).isEqualTo(car.getPossiblePassengers());
    }

    @Transactional
    @DisplayName("차량 변경 테스트")
    @Test
    void editCarTest() {
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

}