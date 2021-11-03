package com.prgrms.broong.management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.repository.LocationRepository;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.repository.SpeciesRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParkCarRepositoryTest {

    private static final int PARK_ORDER = 0;
    private static final int CAR_COUNT = 2;

    @Autowired
    private ParkCarRepository parkCarRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private LocationRepository locationRepository;

    private Car car;
    private Car car2;
    private Park park;
    private Park park2;
    private ParkCar parkCar;
    private Species species;

    @BeforeEach
    void setUp() {
        species = Species.builder()
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
        carRepository.save(car);

        car2 = Car.builder()
            .carNum("99허124333")
            .fuel(900L)
            .model("QM6")
            .price(1000L)
            .possiblePassengers(10)
            .species(species)
            .build();
        carRepository.save(car2);

        Location location = Location.builder()
            .cityId("1")
            .townId("101")
            .locationName("도봉구")
            .build();
        locationRepository.save(location);

        park = Park.builder()
            .possibleNum(10)
            .location(location)
            .build();
        parkRepository.save(park);

        park2 = Park.builder()
            .possibleNum(20)
            .location(location)
            .build();
        parkRepository.save(park2);

        Park park3 = Park.builder()
            .possibleNum(30)
            .location(location)
            .build();
        parkRepository.save(park3);

        parkCar = ParkCar.builder()
            .car(car)
            .park(park)
            .build();
        parkCarRepository.save(parkCar);

        parkCar = ParkCar.builder()
            .car(car2)
            .park(park)
            .build();
        parkCarRepository.save(parkCar);

        parkCar = ParkCar.builder()
            .car(car2)
            .park(park2)
            .build();
        parkCarRepository.save(parkCar);
    }

    @DisplayName("ParkCar 저장 테스트")
    @Test
    void saveParkCarTest() {
        //then
        List<ParkCar> getParkCars = parkCarRepository.findAll();

        assertThat(getParkCars).hasSize(3);
    }

//    @DisplayName("주차장 다건 조회 및 주차장별 차량 개수 테스트")
//    @Test
//    void getParksWithCountTest() {
//        //then
//        Pageable pageable = PageRequest.of(0, 5);
//        Page<ParksInfoDto> getParks = parkCarRepository.findParksWithCarCount(pageable);
//
////        assertThat(getParks.get(PARK_ORDER).getCnt()).isEqualTo(CAR_COUNT);
//    }

    @DisplayName("주차장별 차량 단건 조회 테스트")
    @Test
    void getParkCarByParkIdAndCarIdTest() {
        //then
        ParkCar getParkCar = parkCarRepository.findParkCarByParkIdAndCarId(
            parkCar.getPark().getId(), car2.getId()).get();

        assertThat(getParkCar.getCar().getCarNum()).isEqualTo(car2.getCarNum());
        assertThat(getParkCar.getCar().getModel()).isEqualTo(car2.getModel());
        assertThat(getParkCar.getCar().getFuel()).isEqualTo(car2.getFuel());
        assertThat(getParkCar.getCar().getPrice()).isEqualTo(car2.getPrice());
    }

    @DisplayName("주차장별 차량 다건 조회 테스트")
    @Test
    void getParkCarsByParkIdTest() {
        //then
        List<ParkCar> getParkCarByParkId = parkCarRepository.findParkCarByParkId(
            park.getId());

        assertThat(getParkCarByParkId).hasSize(CAR_COUNT);
    }

    @DisplayName("주차장별 필터 적용 조회 테스트")
    @Test
    void getParkCarByFilterTest() {
        //then
        List<ParkCar> getParkCar = parkCarRepository.findParkCarByParkIdAndSpeciesName(
            parkCar.getPark().getId(), parkCar.getCar().getSpecies().getId());

//        assertThat(getParkCar.get(0).getCar().getSpecies(), samePropertyValuesAs(species));
    }

}