package com.prgrms.broong.management.park.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.broong.management.park.domain.Location;
import com.prgrms.broong.management.park.domain.Park;
import com.prgrms.broong.management.park.dto.ParkUpdateDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ParkRepositoryTest {

    @Autowired
    ParkRepository parkRepository;

    private Park park;

    @BeforeEach
    void setUp() {
        Location location = Location.builder()
            .cityId("1")
            .townId("101")
            .locationName("도봉구")
            .build();

        park = Park.builder()
            .possibleNum(10)
            .location(location)
            .build();
        parkRepository.save(park);
    }

    @AfterEach
    void tearDown() {
        parkRepository.deleteAll();
    }

    @DisplayName("주차장 생성 테스트")
    @Test
    void saveParkTest() {
        //then
        List<Park> parks = parkRepository.findAll();

        assertThat(parks).hasSize(1);
    }

    @DisplayName("주차장 조회 테스트")
    @Test
    void getParkTest() {
        //then
        Park getPark = parkRepository.findById(park.getId()).get();

        assertThat(getPark.getPossibleNum()).isEqualTo(park.getPossibleNum());
    }

    @Transactional
    @DisplayName("주차장 변경 테스트")
    @Test
    void editParkTest() {
        //when
        park.changeParkInfo(
            ParkUpdateDto.builder()
                .possibleNum(100)
                .build()
        );

        //then
        Park getPark = parkRepository.findById(park.getId()).get();

        assertThat(getPark.getPossibleNum()).isEqualTo(park.getPossibleNum());
    }

}