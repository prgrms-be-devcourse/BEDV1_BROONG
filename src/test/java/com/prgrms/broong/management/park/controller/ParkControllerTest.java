package com.prgrms.broong.management.park.controller;

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
import com.prgrms.broong.management.park.dto.LocationDto;
import com.prgrms.broong.management.park.dto.ParkRequestDto;
import com.prgrms.broong.management.park.dto.ParkUpdateDto;
import com.prgrms.broong.management.park.repository.LocationRepository;
import com.prgrms.broong.management.park.service.ParkService;
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
class ParkControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ParkService parkService;

    private ParkRequestDto parkRequestDto;

    @BeforeEach
    void setup() {
        parkRequestDto = ParkRequestDto.builder()
            .possibleNum(5)
            .locationDto(
                LocationDto.builder()
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
        //when //then
        mockMvc.perform(post("/api/v1/parks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("park-save",
                requestFields(
                    fieldWithPath("possibleNum").type(JsonFieldType.NUMBER)
                        .description("주차장 수용가능한 차량 수"),
                    fieldWithPath("locationDto").type(JsonFieldType.OBJECT)
                        .description("위치 정보"),
                    fieldWithPath("locationDto.id").type(JsonFieldType.NULL).description("위치 Id"),
                    fieldWithPath("locationDto.cityId").type(JsonFieldType.STRING)
                        .description("시 Id"),
                    fieldWithPath("locationDto.townId").type(JsonFieldType.STRING)
                        .description("구 Id"),
                    fieldWithPath("locationDto.locationName").type(JsonFieldType.STRING)
                        .description("위치 이름")
                ),
                responseFields(
                    fieldWithPath("parkId").type(JsonFieldType.NUMBER).description("주차장 id")
                )
            ));
    }

    @Test
    @DisplayName("Park 컨트롤러 조회 테스트")
    void getParkById() throws Exception {
        //given
        Long id = parkService.savePark(parkRequestDto);

        //when //then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/parks/{parkId}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("park-find",
                pathParameters(
                    parameterWithName("parkId").description("주차장 Id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("주차장 id"),
                    fieldWithPath("possibleNum").type(JsonFieldType.NUMBER)
                        .description("주차장 수용가능한 차량 수"),
                    fieldWithPath("locationDto").type(JsonFieldType.OBJECT)
                        .description("위치 정보"),
                    fieldWithPath("locationDto.id").type(JsonFieldType.NUMBER).description("위치 Id"),
                    fieldWithPath("locationDto.cityId").type(JsonFieldType.STRING)
                        .description("시 Id"),
                    fieldWithPath("locationDto.townId").type(JsonFieldType.STRING)
                        .description("구 Id"),
                    fieldWithPath("locationDto.locationName").type(JsonFieldType.STRING)
                        .description("위치 이름")
                )
            ));
    }

    @Test
    @DisplayName("Park 컨트롤러 update 테스트")
    void editParkTest() throws Exception {
        //given
        Long id = parkService.savePark(parkRequestDto);

        ParkUpdateDto parkUpdateDto = ParkUpdateDto.builder()
            .possibleNum(13)
            .build();

        //when //then
        mockMvc.perform(put("/api/v1/parks/{parkId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkUpdateDto)))
            .andExpect(status().isOk())
            .andDo(document("park-update",
                requestFields(
                    fieldWithPath("possibleNum").type(JsonFieldType.NUMBER)
                        .description("possibleNum")
                ),
                responseFields(
                    fieldWithPath("parkId").type(JsonFieldType.NUMBER).description("주차장 id")
                )
            ));
    }

}