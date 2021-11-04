package com.prgrms.broong.management.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.dto.ParkCarRequestDto;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.dto.ParksInfoDto;
import com.prgrms.broong.management.park.converter.ParkConverter;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.dto.LocationDto;
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

    private static final Long ID = 1L;
    private static final String CITY_ID = "1";
    private static final String TOWN_ID = "101";
    private static final String LOCATION_NAME = "도봉구";
    private static final Integer POSSIBLE_NUM = 3;

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

    private Park park;

    private Car car;

    @BeforeEach
    void setUp() {
        Location location = Location.builder()
            .cityId(CITY_ID)
            .townId(TOWN_ID)
            .locationName(LOCATION_NAME)
            .build();
        locationRepository.save(location);

        park = Park.builder()
            .possibleNum(POSSIBLE_NUM)
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

        LocationDto locationdto = LocationDto.builder()
            .id(ID)
            .cityId("1")
            .townId("101")
            .locationName("송파구")
            .build();

        SpeciesDto speciesDto = SpeciesDto.builder()
            .id(ID)
            .name("중형")
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

        assertThat(result.getPark().getId()).isEqualTo(ID);
        assertThat(result.getCar().getId()).isEqualTo(ID);
        assertThat(result.getCar().getCarNum()).isEqualTo("11허124333");
    }

    @DisplayName("주차장 다건 조회 및 주차장별 차량 개수 테스트")
    @Test
    void findParksWithCarCountTest() {
        //then
        List<ParksInfoDto> parksInfoDtos = parkCarService.getParksWithCarCount();

        assertThat(parksInfoDtos.get(0).getPossibleNum()).isEqualTo(POSSIBLE_NUM);
        assertThat(parksInfoDtos.get(0).getCityId()).isEqualTo(CITY_ID);
        assertThat(parksInfoDtos.get(0).getTownId()).isEqualTo(TOWN_ID);
        assertThat(parksInfoDtos.get(0).getLocationName()).isEqualTo(LOCATION_NAME);
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
        assertThat(getParkCarByParkId.get(0).getParkResponseDto().getId()).isEqualTo(ID);
        assertThat(getParkCarByParkId.get(0).getParkResponseDto()
            .getPossibleNum()).isEqualTo(parkConverter.parkToResponseDto(park).getPossibleNum());
        assertThat(getParkCarByParkId.get(0).getCarResponseDto().getId()).isEqualTo(ID);
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
        assertThat(parkCarRequestDto.getCarResponseDto().getId()).isEqualTo(ID);
        assertThat(parkCarRequestDto.getCarResponseDto().getCarNum()).isEqualTo(
            carConverter.carToResponseDto(car).getCarNum());
        assertThat(parkCarRequestDto.getParkResponseDto().getId(), is(ID));
        assertThat(parkCarRequestDto.getParkResponseDto().getPossibleNum()).isEqualTo(
            parkConverter.parkToResponseDto(park).getPossibleNum());
        assertThat(parkCarRequestDto.getCarResponseDto().getSpeciesDto(),
            samePropertyValuesAs(carConverter.carToResponseDto(car).getSpeciesDto()));
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
            park.getId(), ID);

        //then
        assertThat(getParkCarByParkIdAndSpeciesName.get(0).getParkResponseDto().getId()).isEqualTo(
            ID);
        assertThat(getParkCarByParkIdAndSpeciesName.get(0).getParkResponseDto()
            .getPossibleNum()).isEqualTo(parkConverter.parkToResponseDto(park).getPossibleNum());
        assertThat(getParkCarByParkIdAndSpeciesName.get(0).getCarResponseDto().getId()).isEqualTo(
            ID);
        assertThat(
            getParkCarByParkIdAndSpeciesName.get(0).getCarResponseDto().getCarNum()).isEqualTo(
            carConverter.carToResponseDto(car).getCarNum());
    }

}