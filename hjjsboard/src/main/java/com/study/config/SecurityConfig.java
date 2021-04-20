package com.study.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity // spring security 설정을 위함
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web
        	.ignoring().antMatchers("/board_css/**", "/js/**", "/img/**", "/lib/**", "/error");
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/board/delete.do").hasRole("ADMIN")
                .antMatchers("/board/write.do").hasAnyRole("ADMIN")
                .antMatchers("/board/*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/member/*").permitAll()
                .and() // 로그인 설정
            .formLogin()
                .loginPage("/member/signin.do")
                .usernameParameter("id")
    			.passwordParameter("pw")
                .loginProcessingUrl("/login")
                .successForwardUrl("/member/signin.do")
                .defaultSuccessUrl("/board/list.do")
                .permitAll()
                .and() // 로그아웃 설정
            .logout()
            	.logoutUrl("/logout") // default
                .logoutSuccessUrl("/board/list.do")
                .invalidateHttpSession(true)
                .and()
            .exceptionHandling()
            	.accessDeniedPage("/error") // 예외처리 핸들링
        		.and()
        	.csrf()
        		.ignoringAntMatchers("/comments/**"); // CSRF 토큰 없이 실행
    }
    
    // inmemory 테스트 방식
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
    	auth.inMemoryAuthentication()
    		.withUser("guest").password(bCryptPasswordEncoder().encode("guest")).roles("USER");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { // 비밀번호 암호화 관련 설정
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }
}