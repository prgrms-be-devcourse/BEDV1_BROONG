package com.prgrms.broong.user.controller;

import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserUpdateDto;
import com.prgrms.broong.user.service.UserServiceImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/api/v1/broong/users")
    public void save(@RequestBody @Valid UserRequestDto userRequestDto) {
        userService.saveUser(userRequestDto);
    }

    @GetMapping("/api/v1/broong/users/{user_id}")
    public void getUserById(@PathVariable("user_id") Long userId) {
        userService.getUserById(userId);
    }

    @PutMapping("/api/v1/broong/users/{user_id}")
    public void editUser(@PathVariable("user_id") Long userId,
        @RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.editUser(userId, userUpdateDto);
    }

}