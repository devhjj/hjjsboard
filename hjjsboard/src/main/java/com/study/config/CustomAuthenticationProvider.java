package com.study.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.study.domain.UserDetailsDto;
import com.study.service.UserDetailsServiceImpl;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
	
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	// AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
    	//UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        
        String email = (String)authentication.getPrincipal(); //token.getName();
        String pw = (String)authentication.getCredentials(); //(String) token.getCredentials();
        // UserDetailsService를 통해 DB에서 아이디로 사용자 조회
        UserDetailsDto userDetailsDto = (UserDetailsDto) userDetailsServiceImpl.loadUserByUsername(email);
        
        if (!passwordEncoder.matches(pw, userDetailsDto.getPassword())) {
            throw new BadCredentialsException(userDetailsDto.getUsername() + "Invalid password");
        }
        
        return new UsernamePasswordAuthenticationToken(userDetailsDto, null, userDetailsDto.getAuthorities());
        
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}