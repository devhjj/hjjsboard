package com.study.service;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.domain.UserDto;
import com.study.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
    private UserMapper userMapper;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
	public boolean createUser(UserDto userDto) {
		int queryResult = 0;
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		queryResult = userMapper.createUser(userDto);
		
		return (queryResult == 1) ? true : false;
	}

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
    	logger.debug("emailService");
        return userMapper.findUserByEmail(email);
    }

	@Override
	public boolean updateUserStatus(Map<String, String> map) {
		int queryResult = 0;
		
		if(userMapper.findByEmailAndAuth(map) == 1) {
			queryResult = userMapper.updateUserStatus(map.get("email"));		
		}
		
		return (queryResult == 1) ? true : false;
	}

	@Override
	public boolean updateAuth(Map<String, String> map) {
		int queryResult = 0;
		queryResult = userMapper.updateAuth(map);
		
		return (queryResult == 1) ? true : false;
	}
}