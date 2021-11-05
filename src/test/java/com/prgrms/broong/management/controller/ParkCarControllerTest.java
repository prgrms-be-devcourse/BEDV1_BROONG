package com.prgrms.broong.management.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.dto.CarResponseDto;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.dto.ParkCarRequestDto;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.dto.LocationDto;
import com.prgrms.broong.management.park.dto.ParkResponseDto;
import com.prgrms.broong.management.park.repository.LocationRepository;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.dto.SpeciesDto;
import com.prgrms.broong.management.species.repository.SpeciesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@AutoConfigureMockMvc
@SpringBootTest
class ParkCarControllerTest {

    private static final Long ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private LocationRepository locationRepository;

    private ParkCarRequestDto parkCarRequestDto;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();

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
        carRepository.save(car);

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
        parkRepository.save(park);

        parkCarRequestDto = ParkCarRequestDto.builder()
            .parkResponseDto(
                ParkResponseDto.builder()
                    .id(park.getId())
                    .possibleNum(10)
                    .locationDto(
                        LocationDto.builder()
                            .id(location.getId())
                            .cityId("1")
                            .townId("101")
                            .locationName("도봉구")
                            .build()
                    )
                    .build()
            )
            .carResponseDto(
                CarResponseDto.builder()
                    .id(car.getId())
                    .carNum("11허124333")
                    .model("k5")
                    .price(50000L)
                    .possiblePassengers(10)
                    .speciesDto(
                        SpeciesDto.builder()
                            .id(species.getId())
                            .name("중형")
                            .build()
                    )
                    .build()
            )
            .build();
    }

    @Test
    @DisplayName("parkcar 저장 controller 테스트")
    void saveParkcarTest() throws Exception {
        mockMvc.perform(post("/api/v1/park-cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkCarRequestDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("주차장 다건 조회 및 주차장별 차량 개수 controller 테스트")
    @Test
    void getParksWithCountTest() throws Exception {
        //when
        mockMvc.perform(get("/api/v1/park-cars")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("park 단건, car 단건조회 controller 테스트")
    void getParkCarByParkIdAndCarIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/park-cars/parks/{parkId}/cars/{carId}", ID, ID)
                .contentType(MediaType.APPLICATION_JSON)
                .param("parkId", String.valueOf(ID))
                .param("carId", String.valueOf(ID)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("park 단건, car 다건조회 controller 테스트")
    void getParkCarByParkIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/park-cars/{parkId}", ID)
                .contentType(MediaType.APPLICATION_JSON)
                .param("parkId", String.valueOf(ID)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @DisplayName("parkcar 주차장별 선택한 차종의 차량리스트 조회 테스트")
    @Test
    void getParkCarByParkIdAndSpeciesNameTest() throws Exception {
        //when
        mockMvc.perform(get("/api/v1/park-cars/parks/{parkId}/species/{speciesId}", ID, ID)
                .contentType(MediaType.APPLICATION_JSON)
                .param("parkId", String.valueOf(ID))
                .param("speciesId", String.valueOf(ID)))
            .andExpect(status().isOk())
            .andDo(print());
    }

}