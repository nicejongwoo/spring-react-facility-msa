package com.practice.facility.auth.service;

import com.practice.facility.auth.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto create(UserDto userDto);

}
