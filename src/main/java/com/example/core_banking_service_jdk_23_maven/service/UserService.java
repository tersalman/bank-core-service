package com.example.core_banking_service_jdk_23_maven.service;

import com.example.core_banking_service_jdk_23_maven.model.UserEntity;
import com.example.core_banking_service_jdk_23_maven.model.dto.User;
import com.example.core_banking_service_jdk_23_maven.model.mapper.UserMapper;
import com.example.core_banking_service_jdk_23_maven.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserMapper userMapper = new UserMapper();

    private final UserRepository userRepository;

    public User readUser(String identification) {
        UserEntity userEntity = userRepository.findByIdentificationNumber(identification).get();
        return userMapper.convertToDto(userEntity);
    }

    public List<User> readUsers(Pageable pageable) {
        return userMapper.convertToDtoList(userRepository.findAll(pageable).getContent());
    }


}
