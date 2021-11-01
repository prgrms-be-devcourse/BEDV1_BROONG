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
import com.prgrms.broong.management.park.repository.LocationRepository;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.repository.ParkCarRepository;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.management.species.repository.SpeciesRepository;
import com.prgrms.broong.reservation.converter.ReservationConverter;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
class ReservationServiceTest {

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
            .reservationStatus(ReservationStatus.RESERVATION)
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
    void saveSuccessReservation() {
        //given
        UserResponseDto userResponseDto = new UserConverter().UserToResponseDto(user);
        userResponseDto.getReservations().stream()
            .map(reservation -> new ReservationConverter().ReservationToResponseDto(reservation));

        ParkCarResponseDto parkCarResponseDto = ParkCarResponseDto.builder()
            .id(parkCar.getId())
            .carResponseDto(new CarConverter().carToResponseDto(car))
            .parkResponseDto(new ParkConverter().parkToResponseDto(park))
            .build();

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .reservationStatus(ReservationStatus.RESERVATION)
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
            .checkTime(reservationRequestDto.getStartTime())
            .build();

        //when
        Long reservationSuccessId = reservationService.saveReservation(reservationRequestDto,
            userReservationCheckDto);

        //then
        Long reservationCount = reservationRepository.findAll().stream().count();
        assertThat(reservationSuccessId, samePropertyValuesAs(reservationCount));
    }

    @Test
    @DisplayName("사용자가 중복된 시간으로 예약했을때 저장 실패 테스트")
    void savefailByDuplicateUserReservation() {
        Reservation getReservation = reservationRepository.findById(1L).get();

        User user = userRepository.findById(1L).get();
        Car car = carRepository.findById(1L).get();
        Park park = parkRepository.findById(1L).get();
        UserResponseDto userResponseDto = new UserConverter().UserToResponseDto(user);
        userResponseDto.getReservations().stream()
            .map(reservation -> new ReservationConverter().ReservationToResponseDto(reservation));

        ParkCarResponseDto parkCarResponseDto = ParkCarResponseDto.builder()
            .id(1L)
            .carResponseDto(new CarConverter().carToResponseDto(car))
            .parkResponseDto(new ParkConverter().parkToResponseDto(park))
            .build();

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .reservationStatus(ReservationStatus.RESERVATION)
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
            .checkTime(reservationRequestDto.getStartTime())
            .build();

        //when, then
        try {
            Long reservationSuccessId = reservationService.saveReservation(reservationRequestDto,
                userReservationCheckDto);
        } catch (RuntimeException e) {
            log.info("예약 중복 실패 테스트 에러내용 -> {}", e.getMessage());
        }
    }

    @Test
    @DisplayName("사용자가 선택한 차량이 이미 예약되어있을 경우 저장 실패 테스트")
    void savefailByDuplicateCarReservation() {
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
        Reservation getReservation = reservationRepository.findById(1L).get();
        User user = userRepository.findById(user2.getId()).get();
        Car car = carRepository.findById(1L).get();
        Park park = parkRepository.findById(1L).get();
        UserResponseDto userResponseDto = new UserConverter().UserToResponseDto(user);
        userResponseDto.getReservations().stream()
            .map(reservation -> new ReservationConverter().ReservationToResponseDto(reservation));

        ParkCarResponseDto parkCarResponseDto = ParkCarResponseDto.builder()
            .id(1L)
            .carResponseDto(new CarConverter().carToResponseDto(car))
            .parkResponseDto(new ParkConverter().parkToResponseDto(park))
            .build();

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .reservationStatus(ReservationStatus.RESERVATION)
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
            .checkTime(reservationRequestDto.getStartTime())
            .build();

        //when, then
        try {
            Long reservationSuccessId = reservationService.saveReservation(reservationRequestDto,
                userReservationCheckDto);
        } catch (RuntimeException e) {
            log.info("예약 중복 실패 테스트 에러내용 -> {}", e.getMessage());
        }
    }

    @Test
    void getReservation() {

    }

    @Test
    void getReservationListByUserId() {
    }

    @Test
    void checkReservationByUserId() {
    }

    @Test
    void possibleReservationTimeByCarId() {
    }

    @Test
    void removeReservation() {
    }
}