package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.interceptor.MyInterceptor;

@Configuration
public class WebserviceConfig implements WebMvcConfigurer {
	
    @Autowired
    LoginInterceptor loginInterceptor;
    
    @Autowired
    MyInterceptor myInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/fileBoard/list_");
    	
    	registry.addInterceptor(myInterceptor)
				.addPathPatterns("/fileBoard/insert/*")
				.addPathPatterns("/fileBoard/detail")
    			.addPathPatterns("/fileBoard/update")
    			.addPathPatterns("/fileBoard/mypage");
	
    }
}