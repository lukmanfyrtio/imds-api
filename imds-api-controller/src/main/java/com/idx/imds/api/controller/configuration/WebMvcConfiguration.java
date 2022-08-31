package com.idx.imds.api.controller.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.idx.imds.api.controller.configuration.interceptor.LogInterceptor;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration  implements WebMvcConfigurer{
	@Autowired
    private LogInterceptor logInterceptor;
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST");
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor);
	}
}
