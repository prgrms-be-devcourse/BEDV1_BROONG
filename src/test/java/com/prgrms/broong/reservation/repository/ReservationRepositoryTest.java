package com.prgrms.broong.reservation.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private Park rentPark;

    private Park returnPark;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .email("pinoa1228@naver.com")
            .name("박연수")
            .licenseInfo(true)
            .password("1234")
            .paymentMethod(true)
            .point(13)
            .build();

//        rentPark = Park.builder()
//            .possibleNum(10)
//            .location(null)
//            .build();
//
//        returnPark = Park.builder()
//            .possibleNum(20)
//            .location(null)
//            .build();

        reservation = Reservation.builder()
            .reservationStatus(ReservationStatus.RESERVATION)
            .usagePoint(10)
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now())
            .fee(1000)
            .build();
        user = userRepository.save(user);
    }

    @Test
    @DisplayName("Reservation 생성 후 불러오기")
    void saveReservationTest() {
        //given
        reservation.setUser(user);
        reservation = reservationRepository.save(reservation);

        //then
        Reservation getReservation = reservationRepository.findById(reservation.getId()).get();

        //then
        assertThat(reservation.getId(), samePropertyValuesAs(getReservation.getId()));
        assertThat(reservation.getFee(), samePropertyValuesAs(getReservation.getFee()));
        assertThat(reservation.getStartTime(), samePropertyValuesAs(getReservation.getStartTime()));
        assertThat(reservation.getEndTime(), samePropertyValuesAs(getReservation.getEndTime()));
        //todo localDateTime log error 해결하기

    }

}