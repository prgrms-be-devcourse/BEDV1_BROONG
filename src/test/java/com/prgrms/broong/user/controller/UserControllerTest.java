package com.prgrms.broong.user.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserUpdateDto;
import com.prgrms.broong.user.service.UserService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LocalValidatorFactoryBean validatorFactoryBean;

    @Autowired
    UserService userService;

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
            .build();
    }

    @Test
    @DisplayName("user 컨트롤러 저장 테스트")
    void saveTest() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
            .andExpect(status().isOk())
            .andDo(document("user-save",
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("사용자 패스워드"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("locationName").type(JsonFieldType.STRING)
                        .description("사용자 위치정보"),
                    fieldWithPath("licenseInfo").type(JsonFieldType.BOOLEAN)
                        .description("사용자 면허정보"),
                    fieldWithPath("paymentMethod").type(JsonFieldType.BOOLEAN)
                        .description("사용자 결제수단")
                ),
                responseFields(
                    fieldWithPath("userId").description("사용자 id")
                )
            ));
    }

    @Test
    @DisplayName("user 컨트롤러 조회 테스트")
    void getByIdTest() throws Exception {
        Long id = userService.saveUser(userRequestDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users/{userId}", id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("user-find",
                pathParameters(
                    parameterWithName("userId").description("사용자 id")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("사용자 id"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("사용자 패스워드"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("locationName").type(JsonFieldType.STRING)
                        .description("사용자 위치정보"),
                    fieldWithPath("licenseInfo").type(JsonFieldType.BOOLEAN)
                        .description("사용자 면허정보"),
                    fieldWithPath("paymentMethod").type(JsonFieldType.BOOLEAN)
                        .description("사용자 결제수단"),
                    fieldWithPath("point").type(JsonFieldType.NUMBER).description("point")
                )
            ));
    }

    @Test
    @DisplayName("user 컨트롤러 update 테스트")
    void updateTest() throws Exception {
        //given
        Long id = userService.saveUser(userRequestDto);
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
            .point(15)
            .build();

        mockMvc.perform(patch("/api/v1/users/{userId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .param("user_id", String.valueOf(id))
                .content(objectMapper.writeValueAsString(userUpdateDto)))
            .andExpect(status().isOk())
            .andDo(document("user-update",
                requestFields(
                    fieldWithPath("point").type(JsonFieldType.NUMBER).description("사용자 포인트")
                ),

                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("사용자 id"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
                    fieldWithPath("password").type(JsonFieldType.STRING).description("사용자 패스워드"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("locationName").type(JsonFieldType.STRING)
                        .description("사용자 위치정보"),
                    fieldWithPath("licenseInfo").type(JsonFieldType.BOOLEAN)
                        .description("사용자 면허정보"),
                    fieldWithPath("paymentMethod").type(JsonFieldType.BOOLEAN)
                        .description("사용자 결제수단"),
                    fieldWithPath("point").type(JsonFieldType.NUMBER).description("point")
                )
            ));
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

        System.out.println("hasErrors(): " + error.hasErrors());

        error.getAllErrors().forEach(e -> {
            System.out.println("=== Error Code ===");
            Arrays.stream(e.getCodes()).forEach(System.out::println);
            System.out.println(e.getDefaultMessage());
        });

    }

}