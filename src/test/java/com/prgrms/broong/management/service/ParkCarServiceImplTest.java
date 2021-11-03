package com.prgrms.broong.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.dto.ParkCarRequestDto;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.park.converter.ParkConverter;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.dto.LocationDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import com.prgrms.broong.management.park.repository.LocationRepository;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.repository.ParkCarRepository;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.dto.SpeciesDto;
import com.prgrms.broong.management.species.repository.SpeciesRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ParkCarServiceImplTest {

    @Autowired
    private ParkCarService parkCarService;

    @Autowired
    private ParkCarRepository parkCarRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ParkConverter parkConverter;

    @Autowired
    private CarConverter carConverter;

    private ParkCarRequestDto parkCarRequestDto;
    private ParkResponseDto parkResponseDto;
    private CarResponseDto carResponseDto;
    private LocationDto locationdto;
    private SpeciesDto speciesDto;
    private Park park;
    private Car car;

    @BeforeEach
    void setUp() {
        Location location = Location.builder()
            .cityId("1")
            .townId("101")
            .locationName("도봉구")
            .build();
        locationRepository.save(location);

        park = Park.builder()
            .possibleNum(3)
            .location(location)
            .build();
        park = parkRepository.save(park);

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
        car = carRepository.save(car);

        locationdto = LocationDto.builder()
            .id(1L)
            .cityId("1")
            .townId("101")
            .locationName("송파구")
            .build();

        speciesDto = SpeciesDto.builder()
            .id(1L)
            .name("중형")
            .build();

        parkResponseDto = ParkResponseDto.builder()
            .id(1L)
            .possibleNum(3)
            .locationDto(locationdto)
            .build();

        carResponseDto = CarResponseDto.builder()
            .id(1L)
            .speciesDto(speciesDto)
            .carNum("11허124333")
            .fuel(100L)
            .model("k5")
            .price(50000L)
            .possiblePassengers(10)
            .build();

        parkCarRequestDto = ParkCarRequestDto.builder()
            .parkResponseDto(parkConverter.parkToResponseDto(park))
            .carResponseDto(carConverter.carToResponseDto(car))
            .build();
    }

    @Test
    @Transactional
    @DisplayName("주차장과 차량 테이블 저장 테스트")
    void save() {
        //given//when
        Long id = parkCarService.saveParkCar(parkCarRequestDto);

        //Then
        ParkCar result = parkCarRepository.findById(id).get();

        assertThat(result.getCar().getCarNum(), is("11허124333"));
        assertThat(result.getPark().getId(), is(1L));
        assertThat(result.getCar().getId(), is(1L));
        assertThat(result.getCar().getId(), is(1L));
    }

    //패스
    @DisplayName("주차장 다건 조회 및 주차장별 차량 개수 테스트")
    @Test
    void findParksWithCarCountTest() {
        //given

        //when

        //then
    }

    @DisplayName("주차장별 차량 다건 조회 테스트")
    @Test
    void findParkCarByParkIdTest() {
        //given
        Long id = parkCarService.saveParkCar(parkCarRequestDto);

        //when
        List<ParkCarResponseDto> getParkCarByParkId = parkCarService.getParkCarByParkId(
            park.getId());

        //then
        assertThat(getParkCarByParkId.get(0).getParkResponseDto().getId()).isEqualTo(
            1L);
        assertThat(getParkCarByParkId.get(0).getParkResponseDto()
            .getPossibleNum()).isEqualTo(parkConverter.parkToResponseDto(park).getPossibleNum());
        assertThat(getParkCarByParkId.get(0).getCarResponseDto().getId()).isEqualTo(
            1L);
        assertThat(
            getParkCarByParkId.get(0).getCarResponseDto().getCarNum()).isEqualTo(
            carConverter.carToResponseDto(car).getCarNum());
    }

    @DisplayName("주차장별 차량 단건 조회 테스트")
    @Test
    void findParkCarByParkIdAndCarIdTest() {
        //given
        Long id = parkCarService.saveParkCar(parkCarRequestDto);

        //when
        ParkCarResponseDto parkCarRequestDto = parkCarService.getParkCarByParkIdAndCarId(
            park.getId(),
            car.getId());

        //then
        assertThat(parkCarRequestDto.getCarResponseDto().getId(), is(1L));

        assertThat(parkCarRequestDto.getCarResponseDto().getCarNum(),
            is(carConverter.carToResponseDto(car).getCarNum()));

        assertThat(parkCarRequestDto.getCarResponseDto().getSpeciesDto(),
            samePropertyValuesAs(carConverter.carToResponseDto(car).getSpeciesDto()));

        assertThat(parkCarRequestDto.getParkResponseDto().getId(), is(1L));

        assertThat(parkCarRequestDto.getParkResponseDto().getPossibleNum(),
            is(parkConverter.parkToResponseDto(park).getPossibleNum()));

        assertThat(parkCarRequestDto.getParkResponseDto().getLocationDto(),
            samePropertyValuesAs(parkConverter.parkToResponseDto(park).getLocationDto()));
    }

    @DisplayName("주차장별 필터 적용 조회 테스트")
    @Test
    void findParkCarByParkIdAndSpeciesNameTest() {
        //given
        Long id = parkCarService.saveParkCar(parkCarRequestDto);

        //when
        List<ParkCarResponseDto> getParkCarByParkIdAndSpeciesName = parkCarService.getParkCarByParkIdAndSpeciesName(
            park.getId(), 1L);

        //then
        assertThat(getParkCarByParkIdAndSpeciesName.get(0).getParkResponseDto().getId()).isEqualTo(
            1L);
        assertThat(getParkCarByParkIdAndSpeciesName.get(0).getParkResponseDto()
            .getPossibleNum()).isEqualTo(parkConverter.parkToResponseDto(park).getPossibleNum());
        assertThat(getParkCarByParkIdAndSpeciesName.get(0).getCarResponseDto().getId()).isEqualTo(
            1L);
        assertThat(
            getParkCarByParkIdAndSpeciesName.get(0).getCarResponseDto().getCarNum()).isEqualTo(
            carConverter.carToResponseDto(car).getCarNum());
    }

}