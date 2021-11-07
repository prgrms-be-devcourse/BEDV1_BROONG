package com.prgrms.broong.reservation.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

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
import com.prgrms.broong.reservation.dto.ReservationResponseDto;
import com.prgrms.broong.reservation.repository.ReservationRepository;
import com.prgrms.broong.user.convert.UserConverter;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
class ReservationServiceTest {

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
    @DisplayName("사용자 예약 성공 테스트")
    void saveSuccessReservationTest() {
        //given
        UserResponseDto userResponseDto = userConverter.UserToResponseDtoWithoutReservationList(
            user);

        ParkCarResponseDto parkCarResponseDto = ParkCarResponseDto.builder()
            .id(parkCar.getId())
            .carResponseDto(carConverter.carToResponseDto(car))
            .parkResponseDto(parkConverter.parkToResponseDto(park))
            .build();

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .reservationStatus(ReservationStatus.READY)
            .startTime(LocalDateTime.now().plusHours(4L))
            .endTime(LocalDateTime.now().plusHours(5L))
            .userResponseDto(userResponseDto)
            .parkCarResponseDto(parkCarResponseDto)
            .fee(300000)
            .usagePoint(100)
            .isOneway(false)
            .build();

        UserReservationCheckDto userReservationCheckDto = UserReservationCheckDto.builder()
            .id(user.getId())
            .checkStartTime(reservationRequestDto.getStartTime())
            .checkEndTime(reservationRequestDto.getEndTime())
            .build();

        //when
        Long reservationSuccessId = reservationService.saveReservation(reservationRequestDto);

        //then
        Long reservationCount = reservationRepository.findAll().stream().count();
        assertThat(reservationSuccessId, samePropertyValuesAs(reservationCount));
    }

    @Test
    @DisplayName("사용자가 중복된 시간으로 예약했을때 저장 실패 테스트")
    void saveFailByDuplicateUserReservationTest() {
        Reservation getReservation = reservationRepository.findById(reservation.getId()).get();

        User getUser = userRepository.findById(user.getId()).get();
        Car getCar = carRepository.findById(car.getId()).get();
        Park getPark = parkRepository.findById(park.getId()).get();
        UserResponseDto userResponseDto = userConverter.UserToResponseDtoWithoutReservationList(
            user);

        ParkCarResponseDto parkCarResponseDto = ParkCarResponseDto.builder()
            .id(1L)
            .carResponseDto(carConverter.carToResponseDto(getCar))
            .parkResponseDto(parkConverter.parkToResponseDto(getPark))
            .build();

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .reservationStatus(ReservationStatus.READY)
            .startTime(getReservation.getStartTime().plusHours(1))
            .endTime(getReservation.getEndTime().plusHours(4))
            .userResponseDto(userResponseDto)
            .parkCarResponseDto(parkCarResponseDto)
            .fee(300000)
            .usagePoint(100)
            .isOneway(false)
            .build();

        UserReservationCheckDto userReservationCheckDto = UserReservationCheckDto.builder()
            .id(getUser.getId())
            .checkStartTime(reservationRequestDto.getStartTime())
            .checkEndTime(reservationRequestDto.getEndTime())
            .build();

        //when, then
        try {
            Long reservationSuccessId = reservationService.saveReservation(reservationRequestDto);
        } catch (RuntimeException e) {
            log.info("예약 중복 실패 테스트 에러내용 -> {}", e.getMessage());
        }
    }

    @Test
    @DisplayName("사용자가 선택한 차량이 이미 예약되어있을 경우 저장 실패 테스트")
    void savefailByDuplicateCarReservationTest() {
        User user2 = User.builder()
            .email("dbwlgna98@naver.com")
            .name("유지훈")
            .locationName("12121")
            .licenseInfo(true)
            .password("12222")
            .paymentMethod(true)
            .point(134000)
            .build();
        user2 = userRepository.save(user2);
        Reservation getReservation = reservationRepository.findById(reservation.getId()).get();
        User user = userRepository.findById(user2.getId()).get();
        Car getCar = carRepository.findById(car.getId()).get();
        Park getPark = parkRepository.findById(park.getId()).get();
        UserResponseDto userResponseDto = userConverter.UserToResponseDtoWithoutReservationList(
            user);

        ParkCarResponseDto parkCarResponseDto = ParkCarResponseDto.builder()
            .id(1L)
            .carResponseDto(carConverter.carToResponseDto(getCar))
            .parkResponseDto(parkConverter.parkToResponseDto(getPark))
            .build();

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .reservationStatus(ReservationStatus.READY)
            .startTime(getReservation.getStartTime().plusHours(1))
            .endTime(getReservation.getEndTime().plusHours(4))
            .userResponseDto(userResponseDto)
            .parkCarResponseDto(parkCarResponseDto)
            .fee(300000)
            .usagePoint(100)
            .isOneway(false)
            .build();

        UserReservationCheckDto userReservationCheckDto = UserReservationCheckDto.builder()
            .id(user.getId())
            .checkStartTime(reservationRequestDto.getStartTime())
            .checkEndTime(reservationRequestDto.getEndTime())
            .build();

        //when, then
        try {
            Long reservationSuccessId = reservationService.saveReservation(reservationRequestDto);
        } catch (RuntimeException e) {
            log.info("예약 중복 실패 테스트 에러내용 -> {}", e.getMessage());
        }
    }

    @Test
    @DisplayName("예약 단건 조회 테스트")
    void getReservationTest() {
        //given, when
        ReservationResponseDto findReservation = reservationService.getReservation(
            reservationService.getReservation(reservation.getId()).getId());

        //then
        assertThat(reservation.getReservationStatus(),
            samePropertyValuesAs(findReservation.getReservationStatus()));
        assertThat(reservation.getId(), samePropertyValuesAs(findReservation.getId()));
        assertThat(reservation.getUsagePoint(),
            samePropertyValuesAs(findReservation.getUsagePoint()));
        assertThat(reservation.getFee(), samePropertyValuesAs(findReservation.getFee()));
        assertThat(reservation.getStartTime(),
            samePropertyValuesAs(findReservation.getStartTime()));
        assertThat(reservation.getEndTime(), samePropertyValuesAs(findReservation.getEndTime()));
    }

    @Test
    @DisplayName("사용자의 예약 내역 조회 테스트")
    void getReservationListByUserIdTest() {
        //given
        saveSuccessReservationTest();

        //when
        Page<ReservationResponseDto> reservationListByUserId = reservationService.getReservationListByUserId(
            user.getId(), 0, 30);

        //then
        assertThat(reservationListByUserId.stream().count(), samePropertyValuesAs(2L));
    }

    @Test
    @DisplayName("사용자의 예약 가능 확인 테스트")
    void checkReservationByUserIdTest() {
        //given
        UserReservationCheckDto userReservationCheckDto = UserReservationCheckDto.builder()
            .id(user.getId())
            .checkStartTime(LocalDateTime.now().plusHours(4L))
            .checkEndTime(LocalDateTime.now().plusHours(7L))
            .build();

        //when
        boolean check = reservationService.checkReservationByUserId(userReservationCheckDto);

        //then
        assertThat(check, samePropertyValuesAs(true));
    }

    @Test
    @DisplayName("특정 자동차의 예약 가능 확인")
    void possibleReservationTimeByCarIdTest() {
        //given, when
        boolean check = reservationService.possibleReservationTimeByCarId(car.getId(),
            LocalDateTime.now().plusHours(4L), LocalDateTime.now().plusHours(6L));

        //then
        assertThat(check, samePropertyValuesAs(true));
    }

    @Test
    @DisplayName("예약 취소시 DELETE 하지 않고 Reservation 상태값을 CANCEL로 변경")
    void removeReservation() {
        //given, when
        Long cancelReservationId = reservationService.removeReservation(reservation.getId());

        //then
        ReservationResponseDto cancelReservation = reservationService.getReservation(
            cancelReservationId);
        assertThat(cancelReservation.getReservationStatus(),
            samePropertyValuesAs(ReservationStatus.CANCEL));
    }
}