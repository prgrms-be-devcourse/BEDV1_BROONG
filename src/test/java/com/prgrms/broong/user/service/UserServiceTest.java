package com.prgrms.broong.user.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserServiceImpl userService;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setup() {
        userRequestDto = UserRequestDto.builder()
            .email("pinoa1228@naver.com")
            .name("박연수")
            .licenseInfo(true)
            .password("1234")
            .paymentMethod(true)
            .point(13)
            .build();

    }

    @Test
    void save() {
        Long id = userService.save(userRequestDto);
        assertThat(id,is(1L));

    }

    @Test
    void getById(){
        Long id = userService.save(userRequestDto);
        UserResponseDto result = userService.getById(id);
        assertThat(result.getEmail(),is(userRequestDto.getEmail()));
        assertThat(result.getName(),is(userRequestDto.getName()));
        assertThat(result.getPassword(),is(userRequestDto.getPassword()));
        assertThat(result.getPoint(),is(userRequestDto.getPoint()));
        assertThat(result.isLicenseInfo(),is(userRequestDto.isLicenseInfo()));
        assertThat(result.isPaymentMethod(),is(userRequestDto.isPaymentMethod()));


    }

}