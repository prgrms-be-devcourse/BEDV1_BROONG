package com.prgrms.broong.reservation.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.broong.management.car.converter.CarConverter;
import com.prgrms.broong.management.car.domain.Car;
import com.prgrms.broong.management.car.repository.CarRepository;
import com.prgrms.broong.management.dto.ParkCarResponseDto;
import com.prgrms.broong.management.park.converter.ParkConverter;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.repository.ParkRepository;
import com.prgrms.broong.management.repository.ParkCarRepository;
import com.prgrms.broong.reservation.converter.ReservationConverter;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.dto.ReservationRequestDto;
import com.prgrms.broong.user.convert.UserConverter;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.dto.UserReservationCheckDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.user.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ReservationServiceTest {

    UserConverter userConverter;

    ReservationConverter reservationConverter;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private ParkCarRepository parkCarRepository;

    @Test
    @DisplayName("예약 저장 성공 테스트")
    void saveReservation() {
        User user = userRepository.findById(1L).get();
        Car car = carRepository.findById(1L).get();
        Park park = parkRepository.findById(1L).get();
        UserResponseDto userResponseDto = new UserConverter().UserToResponseDto(user);
        userResponseDto.getReservations().stream()
            .map(reservation -> reservationConverter.ReservationToResponseDto(reservation));

        //parCar는 만들어 지면 Dto에 추가해서 만들기
        ParkCarResponseDto parkCarResponseDto = ParkCarResponseDto.builder()
            .id(1L)
            .carResponseDto(new CarConverter().carToResponseDto(car))
            .parkResponseDto(new ParkConverter().parkToResponseDto(park))
            .build();

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
            .reservationStatus(ReservationStatus.RESERVATION)
            .startTime(LocalDateTime.now().minusHours(1))
            .endTime(LocalDateTime.now().minusHours(3))
            .userResponseDto(userResponseDto)
            .parkCarResponseDto(parkCarResponseDto)
            .fee(300000)
            .usagePoint(100)
            .isOneway(false)
            .build();

        UserReservationCheckDto userReservationCheckDto = UserReservationCheckDto.builder()
            .id(user.getId())
            .checkTime(LocalDateTime.now().plusHours(2))
            .build();

        //when
        Long reservationSuccessId = reservationService.saveReservation(reservationRequestDto,
            userReservationCheckDto);

        //then
        assertThat(reservationSuccessId, samePropertyValuesAs(3L));
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