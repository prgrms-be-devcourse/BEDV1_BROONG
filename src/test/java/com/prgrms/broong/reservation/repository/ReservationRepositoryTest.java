package com.prgrms.broong.reservation.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.domain.ParkCar;
import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.repository.ParkCarRepository;
import com.prgrms.broong.management.species.domain.Species;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;


@SpringBootTest
class ReservationRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ParkCarRepository parkCarRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private ParkCar parkCar;

    private Reservation reservation;

    private Reservation reservation2;

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

        Car car = Car.builder()
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

        Park park = Park.builder()
            .possibleNum(10)
            .location(location)
            .build();
        park = parkRepository.save(park);

        parkCar = ParkCar.builder()
            .car(car)
            .park(park)
            .build();
        parkCar = parkCarRepository.save(parkCar);

        reservation = Reservation.builder()
            .reservationStatus(ReservationStatus.READY)
            .usagePoint(1000)
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now().plusHours(2L))
            .parkCar(parkCar)
            .isOneway(true)
            .fee(10000)
            .build();

        reservation2 = Reservation.builder()
            .reservationStatus(ReservationStatus.READY)
            .usagePoint(3000)
            .startTime(LocalDateTime.now().plusHours(5L))
            .endTime(LocalDateTime.now().plusHours(7L))
            .parkCar(parkCar)
            .isOneway(true)
            .fee(40000)
            .build();

        user = userRepository.save(user);
    }

    @Test
    @DisplayName("Reservation 생성 후 불러오기")
    void saveReservationTest() {
        //given
        reservation.registerUser(user);
        reservation = reservationRepository.save(reservation);

        //when
        Reservation getReservation = reservationRepository.findById(reservation.getId()).get();

        //then
        assertThat(reservation.getId(), samePropertyValuesAs(getReservation.getId()));
        assertThat(reservation.getFee(), samePropertyValuesAs(getReservation.getFee()));
        assertThat(reservation.getStartTime(), samePropertyValuesAs(getReservation.getStartTime()));
        assertThat(reservation.getEndTime(), samePropertyValuesAs(getReservation.getEndTime()));
        assertThat(reservation.getReservationStatus(),
            samePropertyValuesAs(getReservation.getReservationStatus()));
    }

    @Test
    @DisplayName("유저Id로 예약된 리스트 조회")
    void findReservationsByUserIdTest() {
        //given
        reservation.registerUser(user);
        reservation2.registerUser(user);
        reservation = reservationRepository.save(reservation);
        reservation2 = reservationRepository.save(reservation2);

        //when
        Page<Reservation> list = reservationRepository.findReservationsByUserId(
            reservation.getUser().getId(), null);

        //then
        assertThat(list.stream().count(), samePropertyValuesAs(2L));
    }

    @Test
    @DisplayName("사용자가 선택한 시간에 예약이 가능한지 확인(예약 중복 확인)")
    void checkReservationByUserIdTest() {
        //given
        reservation.registerUser(user);
        reservation2.registerUser(user);
        reservation = reservationRepository.save(reservation);
        reservation2 = reservationRepository.save(reservation2);

        //when
        Optional<Reservation> duplicateReservation = reservationRepository.checkReservationByUserId(
            user.getId()
            , reservation.getStartTime().plusHours(1), reservation.getEndTime().plusHours(2),
            ReservationStatus.CANCEL);
        Optional<Reservation> possibleReservation = reservationRepository.checkReservationByUserId(
            user.getId()
            , reservation.getStartTime().plusHours(3), reservation.getEndTime().plusHours(4),
            ReservationStatus.CANCEL);

        //then
        assertThat(duplicateReservation.isEmpty(), samePropertyValuesAs(true));
        assertThat(possibleReservation.isPresent(), samePropertyValuesAs(true));
    }

    @Test
    @DisplayName("선택한 차량의 예약 가능 여부 확인")
    void possibleReservationTimeByCarIdTest() {
        //given
        reservation.registerUser(user);
        reservation2.registerUser(user);
        reservation = reservationRepository.save(reservation);
        reservation2 = reservationRepository.save(reservation2);

        //when
        Optional<Reservation> failReservation = reservationRepository.possibleReservationTimeByCarId(
            reservation.getParkCar().getCar().getId()
            , reservation.getStartTime().plusHours(1), reservation.getEndTime().plusHours(2),
            ReservationStatus.CANCEL);
        Optional<Reservation> successReservation = reservationRepository.possibleReservationTimeByCarId(
            reservation.getParkCar().getCar().getId()
            , reservation.getStartTime().plusHours(3), reservation.getEndTime().plusHours(4),
            ReservationStatus.CANCEL);

        //then
        assertThat(failReservation.isEmpty(), samePropertyValuesAs(true));
        assertThat(successReservation.isPresent(), samePropertyValuesAs(true));
    }

    @Test
    @DisplayName("예약 조회시 사용자 정보도 조회")
    void findReservationAndUserTest() {
        //given
        reservation.registerUser(user);
        reservation2.registerUser(user);
        reservation = reservationRepository.save(reservation);
        reservation2 = reservationRepository.save(reservation2);

        //when
        Optional<Reservation> getReservation = reservationRepository.findReservationAndUser(
            reservation.getId());

        //then
        assertThat(getReservation.get().getUser().getName(), samePropertyValuesAs(user.getName()));
        assertThat(getReservation.get().getUser().getEmail(),
            samePropertyValuesAs(user.getEmail()));
        assertThat(getReservation.get().getUser().getPoint(),
            samePropertyValuesAs(user.getPoint()));
        assertThat(getReservation.get().getUser().getLocationName(),
            samePropertyValuesAs(user.getLocationName()));
        assertThat(getReservation.get().getFee(), samePropertyValuesAs(reservation.getFee()));
        assertThat(getReservation.get().getStartTime(),
            samePropertyValuesAs(reservation.getStartTime()));
        assertThat(getReservation.get().getEndTime(),
            samePropertyValuesAs(reservation.getEndTime()));
    }

}