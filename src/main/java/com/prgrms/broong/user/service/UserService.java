package com.prgrms.broong.user.service;

import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.user.dto.UserUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    Long saveUser(UserRequestDto userRequestDto);

    UserResponseDto getUserById(Long id);

    UserResponseDto editUser(Long id, UserUpdateDto userUpdateDto);

}
