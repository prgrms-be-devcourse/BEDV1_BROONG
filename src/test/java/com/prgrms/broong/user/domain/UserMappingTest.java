package com.prgrms.broong.user.domain;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.prgrms.broong.reservation.domain.Reservation;
import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.user.repository.UserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMappingTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void mappingTest() {
        User user = User.builder()
            .email("pinoa1228@naver.com")
            .name("박연수")
            .licenseInfo(true)
            .password("1234")
            .paymentMethod(true)
            .point(13)
            .build();

        Reservation reservation = Reservation.builder()
            .reservationStatus(ReservationStatus.RESERVATION)
            .usagePoint(10)
            .startTime(LocalDateTime.MIN)
            .endTime(LocalDateTime.MAX)
            .fee(1000)
            .build();

        reservation.setUser(user);

        userRepository.save(user);
        assertThat(reservation, samePropertyValuesAs(user.getReservations().get(0)));


    }

    @AfterEach
    void delete() {

        userRepository.deleteAll();
    }

}