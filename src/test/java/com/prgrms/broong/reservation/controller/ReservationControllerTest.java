package com.prgrms.broong.reservation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.prgrms.broong.management.park.repository.LocationRepository;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.repository.ParkCarRepository;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.repository.SpeciesRepository;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.reservation.repository.ReservationRepository;
import com.prgrms.broong.reservation.service.ReservationService;
import com.prgrms.broong.user.convert.UserConverter;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.user.repository.UserRepository;
import java.time.LocalDateTime;
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
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private CarConverter carConverter;

    @Autowired
    private ParkConverter parkConverter;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

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
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();

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

        Location location = Location.builder()
            .cityId("1")
            .townId("101")
            .locationName("도봉구")
            .build();
        locationRepository.save(location);

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
            .andDo(print());
    }

    @Test
    @DisplayName("예약 단건 조회 테스트")
    void getReservationTest() throws Exception {
        mockMvc.perform(get("/api/v1/reservations/{reservationId}", reservation.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("예약 취소시 상태 변경 테스트")
    void cancelReservationTest() throws Exception {
        mockMvc.perform(patch("/api/v1/reservations/{reservationId}", reservation.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("사용자의 예약 조회 리스트")
    void getReservationListByUserIdTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/reservations/users/{userId}", reservation.getUser().getId())
                    .param("page", String.valueOf(0))
                    .param("size", String.valueOf(20))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("사용자의 중복 예약 확인 테스트")
    void checkReservationByUserIdTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/reservations/check-reservations/{userId}",
                    reservation.getUser().getId())
                    .param("checkStartTime", String.valueOf(LocalDateTime.now().plusHours(10)))
                    .param("checkEndTime", String.valueOf(LocalDateTime.now().plusHours(12)))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("선택한 자동차 예약 가능 확인 테스트")
    void possibleReservationByCarIdTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/reservations/possible-reservations/{carId}",
                    reservation.getUser().getId())
                    .param("checkStartTime", String.valueOf(LocalDateTime.now().plusHours(10)))
                    .param("checkEndTime", String.valueOf(LocalDateTime.now().plusHours(12)))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
