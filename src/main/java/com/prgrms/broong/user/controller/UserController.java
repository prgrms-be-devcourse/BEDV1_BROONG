package com.prgrms.broong.user.controller;

import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.user.dto.UserUpdateDto;
import com.prgrms.broong.user.service.UserServiceImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class UserController {


    private final UserServiceImpl userService;

    @PostMapping(path = "/v1/broong/users")
    public ResponseEntity<Long> save(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.saveUser(userRequestDto));
    }

    @GetMapping(path = "/v1/broong/users/{user_id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("user_id") Long userId) {
        log.info("{}", userService.getUserById(userId).getReservationResponseDto().get(0));
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping(path = "/v1/broong/users/{user_id}")
    public ResponseEntity<UserResponseDto> editUser(@PathVariable("user_id") Long userId,
        @RequestBody @Valid UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.editUser(userId, userUpdateDto));
    }

}