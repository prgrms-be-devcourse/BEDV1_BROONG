package com.prgrms.broong.user.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.user.dto.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
            .locationName("101")
            .licenseInfo(true)
            .password("1234")
            .paymentMethod(true)
            .point(13)
            .build();
    }

    @Test
    @DisplayName("User에 저장 할 수 있다.")
    void save() {
        //Given
        // When
        Long id = userService.saveUser(userRequestDto);

        //Then
        assertThat(id, is(1L));
    }

    @Test
    @DisplayName("User를 id로 찾을 수 있다.")
    void getById() {
        //Given
        Long id = userService.saveUser(userRequestDto);

        //When
        UserResponseDto result = userService.getUserById(id);

        //Then
        assertThat(result.getEmail(), is(userRequestDto.getEmail()));
        assertThat(result.getName(), is(userRequestDto.getName()));
        assertThat(result.getPassword(), is(userRequestDto.getPassword()));
        assertThat(result.getPoint(), is(userRequestDto.getPoint()));
        assertThat(result.isLicenseInfo(), is(userRequestDto.isLicenseInfo()));
        assertThat(result.getLocationName(), is(userRequestDto.getLocationName()));
        assertThat(result.isPaymentMethod(), is(userRequestDto.isPaymentMethod()));
    }

    @Test
    @DisplayName("User를 수정 할 수 있다.")
    void editTest() {
        //Given
        Long id = userService.saveUser(userRequestDto);
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .point(0)
            .build();

        //When
        UserResponseDto userResponseDto = userService.editUser(id, userUpdateDto);

        //Then
        assertThat(userResponseDto.getPoint(), is(userUpdateDto.getPoint()));
    }

}