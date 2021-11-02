package com.prgrms.broong.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserUpdateDto;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    LocalValidatorFactoryBean validatorFactoryBean;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();

        userRequestDto = UserRequestDto.builder()
            .email("pinoa1228@naver.com")
            .name("")
            .locationName("101")
            .licenseInfo(true)
            .password("1234")
            .paymentMethod(true)
            .build();

    }

    @Test
    @DisplayName("user 컨트롤러 조회 테스트")
    void getByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/broong/users/{user_id}", 1L)
                .contentType(MediaType.APPLICATION_JSON).param("user_id", String.valueOf(1L)))
            .andExpect(status().is(400))
            .andDo(print());
    }

    @Test
    @DisplayName("user 컨트롤러 저장 테스트")
    void saveTest() throws Exception {
        mockMvc.perform(post("/api/v1/broong/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("user 컨트롤러 update 테스트")
    void updateTest() throws Exception {
        //given
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .point(15)
            .build();

        mockMvc.perform(put("/api/v1/broong/users/{user_id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .param("user_id", String.valueOf(1L))
                .content(objectMapper.writeValueAsString(userUpdateDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }


    @Test
    @DisplayName("validation 테스트")
    void validation() {
        UserRequestDto userRequestDto1 = UserRequestDto.builder()
            .email("pinoa1228.com")
            .name("")
            .locationName("")
            .licenseInfo(true)
            .password("1")
            .paymentMethod(true)
            .build();
        Errors error = new BeanPropertyBindingResult(userRequestDto1,
            "user_request");
        validatorFactoryBean.validate(userRequestDto1, error);

        // 에러가 있는지
        System.out.println("hasErrors(): " + error.hasErrors());

        // 발생한 에러를 순차적으로 순회하며 에러코드와 default message 출력
        error.getAllErrors().forEach(e -> {
            System.out.println("=== Error Code ===");
            Arrays.stream(e.getCodes()).forEach(System.out::println);
            System.out.println(e.getDefaultMessage());
        });

    }

}