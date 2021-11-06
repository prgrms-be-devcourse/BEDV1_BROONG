package com.prgrms.broong.management.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.dto.ParksInfoDto;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.species.domain.Species;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParkCarRepositoryTest {

    @Autowired
    private ParkCarRepository parkCarRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private CarRepository carRepository;

    private Car car;

    private Park park;

    private ParkCar parkCar;

    private Species species;

    @BeforeEach
    void setUp() {
        species = Species.builder()
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

        Location location = Location.builder()
            .cityId("1")
            .townId("101")
            .locationName("도봉구")
            .build();

        park = Park.builder()
            .possibleNum(10)
            .location(location)
            .build();
        parkRepository.save(park);

        parkCar = ParkCar.builder()
            .car(car)
            .park(park)
            .build();
        parkCarRepository.save(parkCar);
    }

    @AfterEach
    void tearDown() {
        parkCarRepository.deleteAll();
        carRepository.deleteAll();
        parkRepository.deleteAll();
    }


    @DisplayName("ParkCar 저장 테스트")
    @Test
    void saveParkCarTest() {
        //then
        List<ParkCar> getParkCars = parkCarRepository.findAll();

        assertThat(getParkCars).hasSize(1);
    }

    @DisplayName("주차장 다건 조회 및 주차장별 차량 개수 테스트")
    @Test
    void getParksWithCountTest() {
        //then
        List<ParksInfoDto> getParks = parkCarRepository.findParksWithCarCount();

        assertThat(getParks.get(0).getCnt()).isEqualTo(1);
    }

    @DisplayName("주차장별 차량 단건 조회 테스트")
    @Test
    void getParkCarByParkIdAndCarIdTest() {
        //then
        ParkCar getParkCar = parkCarRepository.findParkCarByParkIdAndCarId(
            parkCar.getPark().getId(), car.getId()).get();

        assertThat(getParkCar.getCar().getCarNum()).isEqualTo(car.getCarNum());
        assertThat(getParkCar.getCar().getModel()).isEqualTo(car.getModel());
        assertThat(getParkCar.getCar().getFuel()).isEqualTo(car.getFuel());
        assertThat(getParkCar.getCar().getPrice()).isEqualTo(car.getPrice());
    }

    @DisplayName("주차장별 차량 다건 조회 테스트")
    @Test
    void getParkCarsByParkIdTest() {
        //then
        List<ParkCar> getParkCarByParkId = parkCarRepository.findParkCarByParkId(
            park.getId());

        assertThat(getParkCarByParkId).hasSize(1);
    }

    @DisplayName("주차장별 필터 적용 조회 테스트")
    @Test
    void getParkCarByFilterTest() {
        //then
        List<ParkCar> getParkCar = parkCarRepository.findParkCarByParkIdAndSpeciesName(
            parkCar.getPark().getId(), parkCar.getCar().getSpecies().getId());

        assertThat(getParkCar.get(0).getCar().getSpecies(), samePropertyValuesAs(species));
    }

}