package com.prgrms.broong.management.park.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.dto.LocationDto;
import com.prgrms.broong.management.park.dto.ParkRequestDto;
import com.prgrms.broong.management.park.dto.ParkUpdateDto;
import com.prgrms.broong.management.park.repository.LocationRepository;
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
class ParkControllerTest {

    private static Long PARK_ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    LocationRepository locationRepository;

    private ParkRequestDto parkRequestDto;

    private Location location;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();

        location = Location.builder()
            .id(1L)
            .cityId("1")
            .townId("101")
            .locationName("경기도")
            .build();
        locationRepository.save(location);

        parkRequestDto = ParkRequestDto.builder()
            .possibleNum(5)
            .locationDto(
                LocationDto.builder()
                    .id(1L)
                    .cityId("1")
                    .townId("101")
                    .locationName("경기도")
                    .build()
            )
            .build();
    }

    @Test
    @DisplayName("Park 컨트롤러 저장 테스트")
    void saveParkTest() throws Exception {
        mockMvc.perform(post("/api/v1/broong/parks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkRequestDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("Park 컨트롤러 조회 테스트")
    void getParkById() throws Exception {
        mockMvc.perform(get("/api/v1/broong/parks/{parkId}", PARK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .param("parkId", String.valueOf(PARK_ID)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("Park 컨트롤러 update 테스트")
    void editParkTest() throws Exception {
        //given
        ParkUpdateDto parkUpdateDto = ParkUpdateDto.builder()
            .possibleNum(13)
            .build();

        mockMvc.perform(put("/api/v1/broong/parks/{parkId}", PARK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .param("parkId", String.valueOf(PARK_ID))
                .content(objectMapper.writeValueAsString(parkUpdateDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }

}