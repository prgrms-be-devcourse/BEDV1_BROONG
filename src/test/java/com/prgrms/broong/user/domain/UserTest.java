package com.prgrms.broong.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.prgrms.broong.reservation.domain.ReservationStatus;
import com.prgrms.broong.reservation.domain.ReservationUser;
import com.prgrms.broong.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {

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

        ReservationUser reservationUser = ReservationUser.builder()
            .reservationStatus(ReservationStatus.RESERVATION)
            .userPoint(10)
            .fee(1000)
            .build();

        reservationUser.setUser(user);

        userRepository.save(user);
        assertThat(reservationUser, samePropertyValuesAs(user.getReservationUsers().get(0)));


    }

    @AfterEach
    void delete() {

        userRepository.deleteAll();
    }

}