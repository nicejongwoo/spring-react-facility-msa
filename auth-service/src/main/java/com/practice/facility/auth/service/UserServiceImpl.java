package com.practice.facility.auth.service;

import com.practice.facility.auth.dto.UserDto;
import com.practice.facility.auth.model.RoleEntity;
import com.practice.facility.auth.model.RoleName;
import com.practice.facility.auth.model.UserEntity;
import com.practice.facility.auth.repository.RoleRepository;
import com.practice.facility.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDto create(UserDto userDto) {

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setPassword("encrypted_password");

        //Default Role: ROLE_USER
        RoleEntity userRole = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("User Role not set."));

        userEntity.setRoles(Collections.singleton(userRole));

        userRepository.save(userEntity);

        return userDto;
    }

}
