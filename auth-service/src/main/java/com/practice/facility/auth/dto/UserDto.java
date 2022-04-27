package com.practice.facility.auth.dto;

import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String name;
    private String password;
    private String userId;

}
