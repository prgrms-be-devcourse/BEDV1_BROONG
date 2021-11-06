package com.prgrms.broong.management.car.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.car.dto.CarRequestDto;
import com.prgrms.broong.management.car.dto.CarUpdateDto;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.car.service.CarService;
import com.prgrms.broong.management.species.dto.SpeciesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class CarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CarConverter carConverter;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarService carService;

    private CarRequestDto carRequestDto;

    @BeforeEach
    void setup() {
        SpeciesDto speciesDto = SpeciesDto.builder()
            .name("중형")
            .build();

        carRequestDto = CarRequestDto.builder()
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
        //when //then
        mockMvc.perform(post("/api/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("car-save",
                requestFields(
                    fieldWithPath("carNum").type(JsonFieldType.STRING).description("차량 번호"),
                    fieldWithPath("model").type(JsonFieldType.STRING).description("차량 모델"),
                    fieldWithPath("fuel").type(JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER)
                        .description("차량 시간당 가격"),
                    fieldWithPath("possiblePassengers").type(JsonFieldType.NUMBER)
                        .description("차량 수용가능한 인원 수"),
                    fieldWithPath("speciesDto").type(JsonFieldType.OBJECT)
                        .description("차종"),
                    fieldWithPath("speciesDto.id").type(JsonFieldType.NULL)
                        .description("차종 Id"),
                    fieldWithPath("speciesDto.name").type(JsonFieldType.STRING)
                        .description("차종 Name")
                ),
                responseFields(
                    fieldWithPath("carId").description("차량 Id")
                )
            ));
    }

    @Test
    @DisplayName("Car 컨트롤러 조회 테스트")
    void getCarById() throws Exception {
        //given
        Long id = carService.saveCar(carRequestDto);

        //when //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/cars/{carId}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("car-find",
                pathParameters(
                    parameterWithName("carId").description("차량 Id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("차량 Id"),
                    fieldWithPath("carNum").type(JsonFieldType.STRING).description("차량 번호"),
                    fieldWithPath("model").type(JsonFieldType.STRING).description("차량 모델"),
                    fieldWithPath("fuel").type(JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER)
                        .description("차량 시간당 가격"),
                    fieldWithPath("possiblePassengers").type(JsonFieldType.NUMBER)
                        .description("차량 수용가능한 인원 수"),
                    fieldWithPath("speciesDto").type(JsonFieldType.OBJECT)
                        .description("차종"),
                    fieldWithPath("speciesDto.id").type(JsonFieldType.NUMBER)
                        .description("차종 Id"),
                    fieldWithPath("speciesDto.name").type(JsonFieldType.STRING)
                        .description("차종 Name")
                )
            ));
    }

    @Test
    @DisplayName("Car 컨트롤러 update 테스트")
    void editTest() throws Exception {
        //given
        Long id = carService.saveCar(carRequestDto);

        CarUpdateDto carUpdateDto = CarUpdateDto.builder()
            .carNum("99허9999")
            .fuel(100L)
            .price(50000L)
            .build();

        //when //then
        mockMvc.perform(put("/api/v1/cars/{carId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carUpdateDto)))
            .andExpect(status().isOk())
            .andDo(document("car-update",
                requestFields(
                    fieldWithPath("carNum").type(JsonFieldType.STRING).description("차량 번호"),
                    fieldWithPath("fuel").type(JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER)
                        .description("차량 시간당 가격")
                ),
                responseFields(
                    fieldWithPath("carId").description("차량 Id")
                )
            ));
    }

}