package com.prgrms.broong.user.controller;

import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.user.dto.UserUpdateDto;
import com.prgrms.broong.user.service.UserService;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/users")
    public ResponseEntity<Map<String, Long>> save(
        @RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.ok(Map.of("userId", userService.saveUser(userRequestDto)));
    }

    @GetMapping(path = "/users/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping(path = "users/{userId}")
    public ResponseEntity<UserResponseDto> editUser(@PathVariable("userId") Long userId,
        @RequestBody @Valid UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.editUser(userId, userUpdateDto));
    }

}