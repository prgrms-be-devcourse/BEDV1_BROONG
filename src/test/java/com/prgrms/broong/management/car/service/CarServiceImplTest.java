package com.prgrms.broong.management.car.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.dto.CarRequestDto;
import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.car.dto.CarUpdateDto;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.dto.SpeciesDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    private static final Long SPECIES_ID = 1L;
    private static final String SPECIES_NAME = "중형";
    private static final Long CAR_ID = 1L;
    private static final String CAR_NUM = "12범3456";
    private static final Long FUEL = 100L;
    private static final String MODEL = "k5";
    private static final Long PRICE = 5000L;
    private static final Integer POSSIBLE_PASSENGERS = 20;
    private static final String UPDATE_CAR_NUM = "99범4939";

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarConverter carConverter;

    private Car car;

    @BeforeEach
    void setUp() {
        Species species = Species.builder()
            .name(SPECIES_NAME)
            .build();

        car = Car.builder()
            .id(CAR_ID)
            .carNum("11허124333")
            .fuel(100L)
            .model("k5")
            .price(50000L)
            .possiblePassengers(10)
            .species(species)
            .build();
    }

    @DisplayName("차량 생성 테스트")
    @Test
    void saveCarTest() {
        //given
        CarRequestDto carRequestDto = CarRequestDto.builder()
            .carNum(CAR_NUM)
            .model(MODEL)
            .fuel(FUEL)
            .price(PRICE)
            .possiblePassengers(POSSIBLE_PASSENGERS)
            .speciesDto(
                SpeciesDto.builder()
                    .id(SPECIES_ID)
                    .name(SPECIES_NAME)
                    .build())
            .build();
        given(carConverter.carToEntity(carRequestDto)).willReturn(car);
        given(carRepository.save(any(Car.class))).willReturn(car);

        //when
        Long userId = carService.saveCar(carRequestDto);

        //then
        assertThat(userId).isEqualTo(car.getId());
    }

    @DisplayName("차량 조회 테스트")
    @Test
    void getCarTest() {
        //given
        CarResponseDto carResponseDto = CarResponseDto.builder()
            .id(CAR_ID)
            .carNum(CAR_NUM)
            .model(MODEL)
            .fuel(FUEL)
            .price(PRICE)
            .possiblePassengers(POSSIBLE_PASSENGERS)
            .speciesDto(
                SpeciesDto.builder()
                    .id(SPECIES_ID)
                    .name(SPECIES_NAME)
                    .build()
            )
            .build();
        given(carRepository.findById(CAR_ID)).willReturn(Optional.of(car));
        given(carConverter.carToResponseDto(car)).willReturn(carResponseDto);

        //when
        CarResponseDto getCarResponseDto = carService.getCarById(CAR_ID);

        //then
        assertThat(getCarResponseDto, samePropertyValuesAs(carResponseDto));
    }

    @DisplayName("차량 변경 테스트")
    @Test
    void editCarTest() {
        //given
        CarUpdateDto carUpdateDto = CarUpdateDto.builder()
            .carNum(UPDATE_CAR_NUM)
            .fuel(FUEL)
            .price(PRICE)
            .build();
        given(carRepository.findById(anyLong())).willReturn(Optional.of(car));

        //when
        Long editCarNum = carService.editCar(this.car.getId(), carUpdateDto);

        //then
        then(carRepository).should().findById(CAR_ID);
        assertThat(editCarNum).isEqualTo(CAR_ID);
    }

}