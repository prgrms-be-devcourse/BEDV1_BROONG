package com.prgrms.broong.management.car.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.broong.management.car.dto.CarRequestDto;
import com.prgrms.broong.management.car.dto.CarUpdateDto;
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
class CarControllerTest {

    private static final Long CAR_ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    SpeciesRepository speciesRepository;

    private CarRequestDto carRequestDto;
    private SpeciesDto speciesDto;
    private Species species;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();

        species = Species.builder()
            .id(1L)
            .name("중형")
            .build();
        speciesRepository.save(species);

        speciesDto = SpeciesDto.builder()
            .id(1L)
            .name("중형")
            .build();

        carRequestDto = carRequestDto.builder()
            .carNum("11허124333")
            .fuel(100L)
            .model("k5")
            .price(50000L)
            .possiblePassengers(10)
            .speciesDto(speciesDto)
            .build();
    }

    @Test
    @DisplayName("Car 컨트롤러 저장 테스트")
    void saveTest() throws Exception {
        mockMvc.perform(post("/api/v1/broong/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carRequestDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("Car 컨트롤러 조회 테스트")
    void getCarById() throws Exception {
        mockMvc.perform(get("/api/v1/broong/cars/{carId}", CAR_ID)
                .contentType(MediaType.APPLICATION_JSON).param("carId", String.valueOf(CAR_ID)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("Car 컨트롤러 update 테스트")
    void editTest() throws Exception {
        //given
        CarUpdateDto carUpdateDto = CarUpdateDto.builder()
            .carNum("99허9999")
            .fuel(100L)
            .price(50000L)
            .build();

        mockMvc.perform(put("/api/v1/broong/cars/{carId}", CAR_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .param("car_id", String.valueOf(CAR_ID))
                .content(objectMapper.writeValueAsString(carUpdateDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }

}