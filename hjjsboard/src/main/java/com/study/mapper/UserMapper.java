package com.study.mapper;

import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.study.domain.UserDetailsDto;
import com.study.domain.UserDto;

@Mapper
public interface UserMapper {

    public Optional<UserDto> findUserByEmail(String email);
    
    public int createUser(UserDto userDto);
    
    public int updateUserStatus(String email);

	public int updateAuth(Map<String, String> map);

	public int findByEmailAndAuth(Map<String, String> map);
}