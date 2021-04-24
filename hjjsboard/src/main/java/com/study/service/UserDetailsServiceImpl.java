package com.study.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.study.domain.UserDetailsDto;
import com.study.domain.UserDto;
import com.study.mapper.UserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
    	Optional<UserDto> userDto = userMapper.findUserByEmail(email);
        
    	UserDetailsDto userDetailsDto = new UserDetailsDto();
    	
    	if(userDto==null) {
            throw new UsernameNotFoundException(email);
        }else {
        	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(userDto.get().getRole()));
            userDetailsDto.setAuthorities(authorities);
            userDetailsDto.setUsername(userDto.get().getEmail());
        	userDetailsDto.setPassword(userDto.get().getPassword());
        }
    	
    	return userDetailsDto;
    }
}