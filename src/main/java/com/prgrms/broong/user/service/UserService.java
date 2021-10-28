package com.prgrms.broong.user.service;

import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Long save(UserRequestDto userRequestDto);
    UserResponseDto getById(Long id);

}
