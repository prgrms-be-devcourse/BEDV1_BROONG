package com.prgrms.broong.user.service;

import com.prgrms.broong.user.UserConverter;
import com.prgrms.broong.user.domain.User;
import com.prgrms.broong.user.dto.UserRequestDto;
import com.prgrms.broong.user.dto.UserResponseDto;
import com.prgrms.broong.user.dto.UserUpdateDto;
import com.prgrms.broong.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    @Override
    @Transactional
    public Long saveUser(UserRequestDto userRequestDto) {
        return userRepository.save(userConverter.convertUser(userRequestDto)).getId();
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id).map(userConverter::convertUserResponseDto)
            .orElseThrow(() -> new RuntimeException("user을 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public UserResponseDto editUser(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("post를 찾을 수 없어 update에 실패했습니다"));
        user.changeUser(userUpdateDto.getPoint());
        return userConverter.convertUserResponseDto(user);
    }
}
