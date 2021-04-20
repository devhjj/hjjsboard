package com.study.service;

import java.util.Map;
import java.util.Optional;

import com.study.domain.UserDto;

public interface UserService {

	/*
    public UserDto login(UserDto userDto);
    */

    public boolean createUser(UserDto userDto);

    Optional<UserDto> findUserByEmail(String email);

    public boolean updateUserStatus(Map<String, String> map);
    
    public boolean updateAuth(Map<String, String> map);
}