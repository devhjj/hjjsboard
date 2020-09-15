package com.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.study.interceptor.LoggerInterceptor;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer{
	
	LoggerInterceptor li = new LoggerInterceptor();
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(li).addPathPatterns("/*") // 적용할 url
		.excludePathPatterns("/css/**", "/fonts/**", "/img/**", "/js/**", "/plugin/**", "/scripts/**", "/scss/**", "/vendor/**");
		/* WebMvcConfigurer.super.addInterceptors(registry); */
	}
}
