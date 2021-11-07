package com.prgrms.broong.reservation.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.park.converter.ParkConverter;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.repository.ParkCarRepository;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.repository.ReservationRepository;
import com.prgrms.broong.user.convert.UserConverter;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.user.repository.UserRepository;
import java.time.LocalDateTime;
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
class ReservationControllerTest {

    private static final int PAGE_NUM = 0;
    private static final int PAGE_SIZE = 30;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private CarConverter carConverter;
    @Autowired
    private ParkConverter parkConverter;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ParkRepository parkRepository;
    @Autowired
    private ParkCarRepository parkCarRepository;
    private User user;
    private Car car;
    private Park park;
    private ParkCar parkCar;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .email("pinoa1228@naver.com")
            .name("박연수")
            .locationName("12121")
            .licenseInfo(true)
            .password("1234")
            .paymentMethod(true)
            .point(130000)
            .build();

        Species species = Species.builder()
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
        car = carRepository.save(car);

        Location location = Location.builder()
            .cityId("1")
            .townId("101")
            .locationName("도봉구")
            .build();

        park = Park.builder()
            .possibleNum(10)
            .location(location)
            .build();
        park = parkRepository.save(park);

        parkCar = ParkCar.builder()
            .car(car)
            .park(park)
            .build();
        parkCar = parkCarRepository.save(parkCar);
        user = userRepository.save(user);

        reservation = Reservation.builder()
            .reservationStatus(ReservationStatus.READY)
            .usagePoint(1000)
            .startTime(LocalDateTime.now().plusHours(1L))
            .endTime(LocalDateTime.now().plusHours(3L))
            .parkCar(parkCar)
            .isOneway(true)
            .fee(10000)
            .build();

        reservation.registerUser(user);
        reservation = reservationRepository.save(reservation);
    }


    @Test
    @DisplayName("유저 예약 컨트롤러 테스트")
    void saveTest() throws Exception {
        UserResponseDto userResponseDto = userConverter.UserToResponseDtoWithoutReservationList(
            user);
        ParkCarResponseDto parkCarResponseDto = ParkCarResponseDto.builder()
            .id(parkCar.getId())
            .carResponseDto(carConverter.carToResponseDto(car))
            .parkResponseDto(parkConverter.parkToResponseDto(park))
            .build();

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .reservationStatus(ReservationStatus.READY)
            .startTime(LocalDateTime.now().plusHours(6L))
            .endTime(LocalDateTime.now().plusHours(7L))
            .userResponseDto(userResponseDto)
            .parkCarResponseDto(parkCarResponseDto)
            .fee(300000)
            .usagePoint(100)
            .isOneway(false)
            .build();

        mockMvc.perform(post("/api/v1/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservationRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("reservation-save",
                requestFields(
                    fieldWithPath("startTime").type(JsonFieldType.STRING).description("예약 시작 시간"),
                    fieldWithPath("endTime").type(JsonFieldType.STRING).description("예약 끝 시간"),
                    fieldWithPath("usagePoint").type(JsonFieldType.NUMBER).description("사용 포인트"),
                    fieldWithPath("reservationStatus").type(JsonFieldType.STRING)
                        .description("예약 상태"),
                    fieldWithPath("fee").type(JsonFieldType.NUMBER).description("결제 금액"),
                    fieldWithPath("isOneway").type(JsonFieldType.BOOLEAN)
                        .description("편도(true), 왕복(false)"),
                    fieldWithPath("userResponseDto").type(JsonFieldType.OBJECT)
                        .description("사용자 정보"),
                    fieldWithPath("userResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("사용자 ID"),
                    fieldWithPath("userResponseDto.email").type(JsonFieldType.STRING)
                        .description("사용자 email"),
                    fieldWithPath("userResponseDto.password").type(JsonFieldType.STRING)
                        .description("사용자 password"),
                    fieldWithPath("userResponseDto.name").type(JsonFieldType.STRING)
                        .description("사용자 이름"),
                    fieldWithPath("userResponseDto.locationName").type(JsonFieldType.STRING)
                        .description("사용자 주변 지역"),
                    fieldWithPath("userResponseDto.licenseInfo").type(JsonFieldType.BOOLEAN)
                        .description("면허 등록 여부"),
                    fieldWithPath("userResponseDto.paymentMethod").type(JsonFieldType.BOOLEAN)
                        .description("결제 등록 여부"),
                    fieldWithPath("userResponseDto.point").type(JsonFieldType.NUMBER)
                        .description("사용자 포인트"),
                    fieldWithPath("parkCarResponseDto").type(JsonFieldType.OBJECT)
                        .description("주차장-자동차 정보"),
                    fieldWithPath("parkCarResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("주차장-자동차 ID"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto").type(JsonFieldType.OBJECT)
                        .description("주차장 응답 DTO"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.id").type(
                        JsonFieldType.NUMBER).description("주차장 Id"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.possibleNum").type(
                        JsonFieldType.NUMBER).description("주차장 수용가능한 차량 수"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.locationDto").type(
                        JsonFieldType.OBJECT).description("주차장 위치"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.locationDto.id").type(
                        JsonFieldType.NUMBER).description("위치 Id"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.locationDto.cityId").type(
                        JsonFieldType.STRING).description("시 Id"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.locationDto.townId").type(
                        JsonFieldType.STRING).description("구 Id"),
                    fieldWithPath(
                        "parkCarResponseDto.parkResponseDto.locationDto.locationName").type(
                        JsonFieldType.STRING).description("위치 이름"),
                    fieldWithPath("parkCarResponseDto.carResponseDto").type(JsonFieldType.OBJECT)
                        .description("차량 응답 DTO"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("차량 Id"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.carNum").type(
                        JsonFieldType.STRING).description("차량 번호"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.model").type(
                        JsonFieldType.STRING).description("차량 모델명"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.fuel").type(
                        JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.price").type(
                        JsonFieldType.NUMBER).description("차량 시간당 가격"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.possiblePassengers").type(
                        JsonFieldType.NUMBER).description("차량 수용가능한 인원 수"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.speciesDto").type(
                        JsonFieldType.OBJECT).description("차종 DTO"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.speciesDto.id").type(
                        JsonFieldType.NUMBER).description("차종 Id"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.speciesDto.name").type(
                        JsonFieldType.STRING).description("차종 이름")
                ),
                responseFields(
                    fieldWithPath("reservationId").description("예약 ID")
                )
            ));
    }

    @Test
    @DisplayName("예약 단건 조회 테스트")
    void getReservationTest() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/reservations/{reservationId}",
                        reservation.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("reservation-find",
                pathParameters(
                    parameterWithName("reservationId").description("예약 id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("예약 ID"),
                    fieldWithPath("startTime").type(JsonFieldType.STRING).description("예약 시작 시간"),
                    fieldWithPath("endTime").type(JsonFieldType.STRING).description("예약 끝 시간"),
                    fieldWithPath("usagePoint").type(JsonFieldType.NUMBER).description("사용 포인트"),
                    fieldWithPath("reservationStatus").type(JsonFieldType.STRING)
                        .description("예약 상태"),
                    fieldWithPath("fee").type(JsonFieldType.NUMBER).description("결제 금액"),
                    fieldWithPath("isOneway").type(JsonFieldType.BOOLEAN)
                        .description("편도(true), 왕복(false)"),
                    fieldWithPath("userResponseDto").type(JsonFieldType.OBJECT)
                        .description("사용자 정보"),
                    fieldWithPath("userResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("사용자 ID"),
                    fieldWithPath("userResponseDto.email").type(JsonFieldType.STRING)
                        .description("사용자 email"),
                    fieldWithPath("userResponseDto.password").type(JsonFieldType.STRING)
                        .description("사용자 password"),
                    fieldWithPath("userResponseDto.name").type(JsonFieldType.STRING)
                        .description("사용자 이름"),
                    fieldWithPath("userResponseDto.locationName").type(JsonFieldType.STRING)
                        .description("사용자 주변 지역"),
                    fieldWithPath("userResponseDto.licenseInfo").type(JsonFieldType.BOOLEAN)
                        .description("면허 등록 여부"),
                    fieldWithPath("userResponseDto.paymentMethod").type(JsonFieldType.BOOLEAN)
                        .description("결제 등록 여부"),
                    fieldWithPath("userResponseDto.point").type(JsonFieldType.NUMBER)
                        .description("사용자 포인트"),
                    fieldWithPath("parkCarResponseDto").type(JsonFieldType.OBJECT)
                        .description("주차장-자동차 정보"),
                    fieldWithPath("parkCarResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("주차장-자동차 ID"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto").type(JsonFieldType.OBJECT)
                        .description("주차장 응답 DTO"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.id").type(
                        JsonFieldType.NUMBER).description("주차장 Id"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.possibleNum").type(
                        JsonFieldType.NUMBER).description("주차장 수용가능한 차량 수"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.locationDto").type(
                        JsonFieldType.OBJECT).description("주차장 위치"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.locationDto.id").type(
                        JsonFieldType.NUMBER).description("위치 Id"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.locationDto.cityId").type(
                        JsonFieldType.STRING).description("시 Id"),
                    fieldWithPath("parkCarResponseDto.parkResponseDto.locationDto.townId").type(
                        JsonFieldType.STRING).description("구 Id"),
                    fieldWithPath(
                        "parkCarResponseDto.parkResponseDto.locationDto.locationName").type(
                        JsonFieldType.STRING).description("위치 이름"),
                    fieldWithPath("parkCarResponseDto.carResponseDto").type(JsonFieldType.OBJECT)
                        .description("차량 응답 DTO"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("차량 Id"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.carNum").type(
                        JsonFieldType.STRING).description("차량 번호"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.model").type(
                        JsonFieldType.STRING).description("차량 모델명"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.fuel").type(
                        JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.price").type(
                        JsonFieldType.NUMBER).description("차량 시간당 가격"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.possiblePassengers").type(
                        JsonFieldType.NUMBER).description("차량 수용가능한 인원 수"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.speciesDto").type(
                        JsonFieldType.OBJECT).description("차종 DTO"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.speciesDto.id").type(
                        JsonFieldType.NUMBER).description("차종 Id"),
                    fieldWithPath("parkCarResponseDto.carResponseDto.speciesDto.name").type(
                        JsonFieldType.STRING).description("차종 이름")
                )
            ));
    }

    @Test
    @DisplayName("예약 취소시 상태 변경 테스트")
    void cancelReservationTest() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.patch("/api/v1/reservations/{reservationId}",
                        reservation.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("reservation-cancel",
                pathParameters(
                    parameterWithName("reservationId").description("예약 id")
                ),
                responseFields(
                    fieldWithPath("reservationId").type(JsonFieldType.NUMBER).description("예약 ID")
                )
            ));
    }

    @Test
    @DisplayName("사용자의 예약 조회 리스트")
    void getReservationListByUserIdTest() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/reservations/users/{userId}",
                        reservation.getUser().getId())
                    .param("pageNum", String.valueOf(PAGE_NUM))
                    .param("pageSize", String.valueOf(PAGE_SIZE))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("reservation-find-all-by-user",
                pathParameters(
                    parameterWithName("userId").description("예약 id")
                ),
                responseFields(
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("예약 ID"),
                    fieldWithPath("content[].startTime").type(JsonFieldType.STRING)
                        .description("예약 시작 시간"),
                    fieldWithPath("content[].endTime").type(JsonFieldType.STRING)
                        .description("예약 끝 시간"),
                    fieldWithPath("content[].usagePoint").type(JsonFieldType.NUMBER)
                        .description("사용 포인트"),
                    fieldWithPath("content[].reservationStatus").type(JsonFieldType.STRING)
                        .description("예약 상태"),
                    fieldWithPath("content[].fee").type(JsonFieldType.NUMBER).description("결제 금액"),
                    fieldWithPath("content[].isOneway").type(JsonFieldType.BOOLEAN)
                        .description("편도(true), 왕복(false)"),
                    fieldWithPath("content[].userResponseDto").type(JsonFieldType.OBJECT)
                        .description("사용자 정보"),
                    fieldWithPath("content[].userResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("사용자 ID"),
                    fieldWithPath("content[].userResponseDto.email").type(JsonFieldType.STRING)
                        .description("사용자 email"),
                    fieldWithPath("content[].userResponseDto.password").type(JsonFieldType.STRING)
                        .description("사용자 password"),
                    fieldWithPath("content[].userResponseDto.name").type(JsonFieldType.STRING)
                        .description("사용자 이름"),
                    fieldWithPath("content[].userResponseDto.locationName").type(
                        JsonFieldType.STRING).description("사용자 주변 지역"),
                    fieldWithPath("content[].userResponseDto.licenseInfo").type(
                        JsonFieldType.BOOLEAN).description("면허 등록 여부"),
                    fieldWithPath("content[].userResponseDto.paymentMethod").type(
                        JsonFieldType.BOOLEAN).description("결제 등록 여부"),
                    fieldWithPath("content[].userResponseDto.point").type(JsonFieldType.NUMBER)
                        .description("사용자 포인트"),
                    fieldWithPath("content[].parkCarResponseDto").type(JsonFieldType.OBJECT)
                        .description("주차장-자동차 정보"),
                    fieldWithPath("content[].parkCarResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("주차장-자동차 ID"),
                    fieldWithPath("content[].parkCarResponseDto.parkResponseDto").type(
                        JsonFieldType.OBJECT).description("주차장 응답 DTO"),
                    fieldWithPath("content[].parkCarResponseDto.parkResponseDto.id").type(
                        JsonFieldType.NUMBER).description("주차장 Id"),
                    fieldWithPath("content[].parkCarResponseDto.parkResponseDto.possibleNum").type(
                        JsonFieldType.NUMBER).description("주차장 수용가능한 차량 수"),
                    fieldWithPath("content[].parkCarResponseDto.parkResponseDto.locationDto").type(
                        JsonFieldType.OBJECT).description("주차장 위치"),
                    fieldWithPath(
                        "content[].parkCarResponseDto.parkResponseDto.locationDto.id").type(
                        JsonFieldType.NUMBER).description("위치 Id"),
                    fieldWithPath(
                        "content[].parkCarResponseDto.parkResponseDto.locationDto.cityId").type(
                        JsonFieldType.STRING).description("시 Id"),
                    fieldWithPath(
                        "content[].parkCarResponseDto.parkResponseDto.locationDto.townId").type(
                        JsonFieldType.STRING).description("구 Id"),
                    fieldWithPath(
                        "content[].parkCarResponseDto.parkResponseDto.locationDto.locationName").type(
                        JsonFieldType.STRING).description("위치 이름"),
                    fieldWithPath("content[].parkCarResponseDto.carResponseDto").type(
                        JsonFieldType.OBJECT).description("차량 응답 DTO"),
                    fieldWithPath("content[].parkCarResponseDto.carResponseDto.id").type(
                        JsonFieldType.NUMBER).description("차량 Id"),
                    fieldWithPath("content[].parkCarResponseDto.carResponseDto.carNum").type(
                        JsonFieldType.STRING).description("차량 번호"),
                    fieldWithPath("content[].parkCarResponseDto.carResponseDto.model").type(
                        JsonFieldType.STRING).description("차량 모델명"),
                    fieldWithPath("content[].parkCarResponseDto.carResponseDto.fuel").type(
                        JsonFieldType.NUMBER).description("차량 기름양"),
                    fieldWithPath("content[].parkCarResponseDto.carResponseDto.price").type(
                        JsonFieldType.NUMBER).description("차량 시간당 가격"),
                    fieldWithPath(
                        "content[].parkCarResponseDto.carResponseDto.possiblePassengers").type(
                        JsonFieldType.NUMBER).description("차량 수용가능한 인원 수"),
                    fieldWithPath("content[].parkCarResponseDto.carResponseDto.speciesDto").type(
                        JsonFieldType.OBJECT).description("차종 DTO"),
                    fieldWithPath("content[].parkCarResponseDto.carResponseDto.speciesDto.id").type(
                        JsonFieldType.NUMBER).description("차종 Id"),
                    fieldWithPath(
                        "content[].parkCarResponseDto.carResponseDto.speciesDto.name").type(
                        JsonFieldType.STRING).description("차종 이름"),
                    fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("pageable"),
                    fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                    fieldWithPath("totalPages").type(JsonFieldType.NUMBER)
                        .description("totalPages"),
                    fieldWithPath("totalElements").type(JsonFieldType.NUMBER)
                        .description("totalElements"),
                    fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                    fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                    fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("empty"),
                    fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("empty"),
                    fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("empty"),
                    fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER)
                        .description("page offset"),
                    fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER)
                        .description("page 숫자"),
                    fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER)
                        .description("page 크기"),
                    fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN)
                        .description("paged 사용"),
                    fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN)
                        .description("empty"),
                    fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                    fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("empty"),
                    fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("empty"),
                    fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER)
                        .description("numberOfElements"),
                    fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
                    fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty")
                )
            ));
    }

    @Test
    @DisplayName("사용자의 중복 예약 확인 테스트")
    void checkReservationByUserIdTest() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/reservations/check-reservations/{userId}",
                        reservation.getUser().getId())
                    .param("checkStartTime", String.valueOf(LocalDateTime.now().plusHours(10)))
                    .param("checkEndTime", String.valueOf(LocalDateTime.now().plusHours(12)))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("reservation-check-user",
                pathParameters(
                    parameterWithName("userId").description("사용자 ID")
                ),
                requestParameters(
                    parameterWithName("checkStartTime").description("예약 시작 시간"),
                    parameterWithName("checkEndTime").description("예약 끝 시간")
                ),
                responseFields(
                    fieldWithPath("checkUserReservation").type(JsonFieldType.BOOLEAN)
                        .description("사용자 중복 에약 확인")
                )
            ));
    }

    @Test
    @DisplayName("선택한 자동차 예약 가능 확인 테스트")
    void possibleReservationByCarIdTest() throws Exception {
        mockMvc.perform(
                RestDocumentationRequestBuilders.get(
                        "/api/v1/reservations/possible-reservations/{carId}",
                        reservation.getUser().getId())
                    .param("checkStartTime", String.valueOf(LocalDateTime.now().plusHours(10)))
                    .param("checkEndTime", String.valueOf(LocalDateTime.now().plusHours(12)))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("reservation-possible-car",
                pathParameters(
                    parameterWithName("carId").description("자동차 ID")
                ),
                requestParameters(
                    parameterWithName("checkStartTime").description("예약 시작 시간"),
                    parameterWithName("checkEndTime").description("예약 끝 시간")
                ),
                responseFields(
                    fieldWithPath("possibleCarReservation").type(JsonFieldType.BOOLEAN)
                        .description("선택한 자동차 에약 확인")
                )
            ));
    }

}
