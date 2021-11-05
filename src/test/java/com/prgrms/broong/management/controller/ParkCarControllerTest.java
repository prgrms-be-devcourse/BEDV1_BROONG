package com.prgrms.broong.management.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.service.ParkCarService;
import com.prgrms.broong.management.species.domain.Species;
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
class ParkCarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ParkCarService parkCarService;

    private ParkCarRequestDto parkCarRequestDto;

    private Species species;

    private Car car;

    private Park park;

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
    @DisplayName("parkCar 저장 controller 테스트")
    void saveParkCarTest() throws Exception {
        //when //then
        mockMvc.perform(post("/api/v1/park-cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkCarRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("parkCar-save",
                requestFields(
                    fieldWithPath("parkResponseDto").type(JsonFieldType.OBJECT)
                        .description("주차장 응답 DTO"),
                    fieldWithPath("parkResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("주차장 Id"),
                    fieldWithPath("parkResponseDto.possibleNum").type(
                            JsonFieldType.NUMBER)
                        .description("주차장 수용가능한 차량 수"),
                    fieldWithPath("parkResponseDto.locationDto").type(
                            JsonFieldType.OBJECT)
                        .description("주차장 위치"),
                    fieldWithPath("parkResponseDto.locationDto.id").type(
                            JsonFieldType.NUMBER)
                        .description("위치 Id"),
                    fieldWithPath("parkResponseDto.locationDto.cityId").type(
                            JsonFieldType.STRING)
                        .description("시 Id"),
                    fieldWithPath("parkResponseDto.locationDto.townId").type(
                            JsonFieldType.STRING)
                        .description("구 Id"),
                    fieldWithPath("parkResponseDto.locationDto.locationName").type(
                            JsonFieldType.STRING)
                        .description("위치 이름"),
                    fieldWithPath("carResponseDto").type(JsonFieldType.OBJECT)
                        .description("차량 응답 DTO"),
                    fieldWithPath("carResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("차량 Id"),
                    fieldWithPath("carResponseDto.carNum").type(
                            JsonFieldType.STRING)
                        .description("차량 번호"),
                    fieldWithPath("carResponseDto.model").type(
                            JsonFieldType.STRING)
                        .description("차량 모델명"),
                    fieldWithPath("carResponseDto.fuel").type(
                        JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("carResponseDto.price").type(
                        JsonFieldType.NUMBER).description("차량 시간당 가격"),
                    fieldWithPath("carResponseDto.possiblePassengers").type(
                        JsonFieldType.NUMBER).description("차량 수용가능한 인원 수"),
                    fieldWithPath("carResponseDto.speciesDto").type(
                            JsonFieldType.OBJECT)
                        .description("차종 DTO"),
                    fieldWithPath("carResponseDto.speciesDto.id").type(
                            JsonFieldType.NUMBER)
                        .description("차종 Id"),
                    fieldWithPath("carResponseDto.speciesDto.name").type(
                            JsonFieldType.STRING)
                        .description("차종 이름")
                ),
                responseFields(
                    fieldWithPath("parkCarId").description("파크 Id")
                )
            ));
    }

    @DisplayName("주차장 다건 조회 및 주차장별 차량 개수 controller 테스트")
    @Test
    void getParksWithCountTest() throws Exception {
        //given
        parkCarService.saveParkCar(parkCarRequestDto);

        //when //then
        mockMvc.perform(get("/api/v1/park-cars")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("parkCar-count",
                responseFields(
                    fieldWithPath("[].cityId").type(JsonFieldType.STRING)
                        .description("cityId"),
                    fieldWithPath("[].parkId").type(JsonFieldType.NUMBER)
                        .description("parkId"),
                    fieldWithPath("[].possibleNum").type(JsonFieldType.NUMBER)
                        .description("possibleNum"),
                    fieldWithPath("[].cnt").type(JsonFieldType.NUMBER)
                        .description("cnt"),
                    fieldWithPath("[].locationId").type(JsonFieldType.NUMBER)
                        .description("locationId"),
                    fieldWithPath("[].townId").type(JsonFieldType.STRING)
                        .description("townId"),
                    fieldWithPath("[].locationName").type(JsonFieldType.STRING)
                        .description("locationName")
                )
            ));
    }

    @Test
    @DisplayName("park 단건, car 단건조회 controller 테스트")
    void getParkCarByParkIdAndCarIdTest() throws Exception {
        //when //then
        parkCarService.saveParkCar(parkCarRequestDto);
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/park-cars/parks/{parkId}/cars/{carId}",
                        park.getId(), car.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("parkCar-findOnePark-findOneCar",
                pathParameters(
                    parameterWithName("parkId").description("parkId"),
                    parameterWithName("carId").description("carId")),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("파크카 응답 DTO Id"),
                    fieldWithPath("parkResponseDto").type(JsonFieldType.OBJECT)
                        .description("주차장 응답 DTO"),
                    fieldWithPath("parkResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("주차장 Id"),
                    fieldWithPath("parkResponseDto.possibleNum").type(
                            JsonFieldType.NUMBER)
                        .description("주차장 수용가능한 차량 수"),
                    fieldWithPath("parkResponseDto.locationDto").type(
                            JsonFieldType.OBJECT)
                        .description("주차장 위치"),
                    fieldWithPath("parkResponseDto.locationDto.id").type(
                            JsonFieldType.NUMBER)
                        .description("위치 Id"),
                    fieldWithPath("parkResponseDto.locationDto.cityId").type(
                            JsonFieldType.STRING)
                        .description("시 Id"),
                    fieldWithPath("parkResponseDto.locationDto.townId").type(
                            JsonFieldType.STRING)
                        .description("구 Id"),
                    fieldWithPath("parkResponseDto.locationDto.locationName").type(
                            JsonFieldType.STRING)
                        .description("위치 이름"),
                    fieldWithPath("carResponseDto").type(JsonFieldType.OBJECT)
                        .description("차량 응답 DTO"),
                    fieldWithPath("carResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("차량 Id"),
                    fieldWithPath("carResponseDto.carNum").type(
                            JsonFieldType.STRING)
                        .description("차량 번호"),
                    fieldWithPath("carResponseDto.model").type(
                            JsonFieldType.STRING)
                        .description("차량 모델명"),
                    fieldWithPath("carResponseDto.fuel").type(
                        JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("carResponseDto.price").type(
                        JsonFieldType.NUMBER).description("차량 시간당 가격"),
                    fieldWithPath("carResponseDto.possiblePassengers").type(
                        JsonFieldType.NUMBER).description("차량 수용가능한 인원 수"),
                    fieldWithPath("carResponseDto.speciesDto").type(
                            JsonFieldType.OBJECT)
                        .description("차종 DTO"),
                    fieldWithPath("carResponseDto.speciesDto.id").type(
                            JsonFieldType.NUMBER)
                        .description("차종 Id"),
                    fieldWithPath("carResponseDto.speciesDto.name").type(
                            JsonFieldType.STRING)
                        .description("차종 이름")
                )
            ));
    }

    @Test
    @DisplayName("park 단건, car 다건조회 controller 테스트")
    void getParkCarByParkIdTest() throws Exception {
        //given
        parkCarService.saveParkCar(parkCarRequestDto);

        //when //then
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/park-cars/{parkId}", park.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("parkCar-findOnePark-findCars",
                pathParameters(
                    parameterWithName("parkId").description("parkId")
                ),
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("파크카 응답 DTO Id"),
                    fieldWithPath("[].parkResponseDto").type(JsonFieldType.OBJECT)
                        .description("주차장 응답 DTO"),
                    fieldWithPath("[].parkResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("주차장 Id"),
                    fieldWithPath("[].parkResponseDto.possibleNum").type(
                            JsonFieldType.NUMBER)
                        .description("주차장 수용가능한 차량 수"),
                    fieldWithPath("[].parkResponseDto.locationDto").type(
                            JsonFieldType.OBJECT)
                        .description("주차장 위치"),
                    fieldWithPath("[].parkResponseDto.locationDto.id").type(
                            JsonFieldType.NUMBER)
                        .description("위치 Id"),
                    fieldWithPath("[].parkResponseDto.locationDto.cityId").type(
                            JsonFieldType.STRING)
                        .description("시 Id"),
                    fieldWithPath("[].parkResponseDto.locationDto.townId").type(
                            JsonFieldType.STRING)
                        .description("구 Id"),
                    fieldWithPath("[].parkResponseDto.locationDto.locationName").type(
                            JsonFieldType.STRING)
                        .description("위치 이름"),
                    fieldWithPath("[].carResponseDto").type(JsonFieldType.OBJECT)
                        .description("차량 응답 DTO"),
                    fieldWithPath("[].carResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("차량 Id"),
                    fieldWithPath("[].carResponseDto.carNum").type(
                            JsonFieldType.STRING)
                        .description("차량 번호"),
                    fieldWithPath("[].carResponseDto.model").type(
                            JsonFieldType.STRING)
                        .description("차량 모델명"),
                    fieldWithPath("[].carResponseDto.fuel").type(
                        JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("[].carResponseDto.price").type(
                        JsonFieldType.NUMBER).description("차량 시간당 가격"),
                    fieldWithPath("[].carResponseDto.possiblePassengers").type(
                        JsonFieldType.NUMBER).description("차량 수용가능한 인원 수"),
                    fieldWithPath("[].carResponseDto.speciesDto").type(
                            JsonFieldType.OBJECT)
                        .description("차종 DTO"),
                    fieldWithPath("[].carResponseDto.speciesDto.id").type(
                            JsonFieldType.NUMBER)
                        .description("차종 Id"),
                    fieldWithPath("[].carResponseDto.speciesDto.name").type(
                            JsonFieldType.STRING)
                        .description("차종 이름")
                )
            ));
    }

    @DisplayName("parkCar 주차장별 선택한 차종의 차량리스트 조회 테스트")
    @Test
    void getParkCarByParkIdAndSpeciesNameTest() throws Exception {
        //given
        parkCarService.saveParkCar(parkCarRequestDto);

        //when //then
        mockMvc.perform(RestDocumentationRequestBuilders.get(
                    "/api/v1/park-cars/parks/{parkId}/species/{speciesId}", park.getId(), species.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("parkCar-filter",
                pathParameters(
                    parameterWithName("parkId").description("주차장 Id"),
                    parameterWithName("speciesId").description("차종 Id")
                ),
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("파크카 응답 DTO Id"),
                    fieldWithPath("[].parkResponseDto").type(JsonFieldType.OBJECT)
                        .description("주차장 응답 DTO"),
                    fieldWithPath("[].parkResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("주차장 Id"),
                    fieldWithPath("[].parkResponseDto.possibleNum").type(
                            JsonFieldType.NUMBER)
                        .description("주차장 수용가능한 차량 수"),
                    fieldWithPath("[].parkResponseDto.locationDto").type(
                            JsonFieldType.OBJECT)
                        .description("주차장 위치"),
                    fieldWithPath("[].parkResponseDto.locationDto.id").type(
                            JsonFieldType.NUMBER)
                        .description("위치 Id"),
                    fieldWithPath("[].parkResponseDto.locationDto.cityId").type(
                            JsonFieldType.STRING)
                        .description("시 Id"),
                    fieldWithPath("[].parkResponseDto.locationDto.townId").type(
                            JsonFieldType.STRING)
                        .description("구 Id"),
                    fieldWithPath("[].parkResponseDto.locationDto.locationName").type(
                            JsonFieldType.STRING)
                        .description("위치 이름"),
                    fieldWithPath("[].carResponseDto").type(JsonFieldType.OBJECT)
                        .description("차량 응답 DTO"),
                    fieldWithPath("[].carResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("차량 Id"),
                    fieldWithPath("[].carResponseDto.carNum").type(
                            JsonFieldType.STRING)
                        .description("차량 번호"),
                    fieldWithPath("[].carResponseDto.model").type(
                            JsonFieldType.STRING)
                        .description("차량 모델명"),
                    fieldWithPath("[].carResponseDto.fuel").type(
                        JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("[].carResponseDto.price").type(
                        JsonFieldType.NUMBER).description("차량 시간당 가격"),
                    fieldWithPath("[].carResponseDto.possiblePassengers").type(
                        JsonFieldType.NUMBER).description("차량 수용가능한 인원 수"),
                    fieldWithPath("[].carResponseDto.speciesDto").type(
                            JsonFieldType.OBJECT)
                        .description("차종 DTO"),
                    fieldWithPath("[].carResponseDto.speciesDto.id").type(
                            JsonFieldType.NUMBER)
                        .description("차종 Id"),
                    fieldWithPath("[].carResponseDto.speciesDto.name").type(
                            JsonFieldType.STRING)
                        .description("차종 이름")
                )
            ));
    }

}