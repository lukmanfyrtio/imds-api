package com.idx.imds.api.controller.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idx.imds.api.controller.contract.BaseResponse;
import com.idx.imds.api.controller.log.LoggingService;
import com.idx.imds.api.core.enums.ImdsErrorEnum;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException)
			throws IOException, ServletException {

		LoggingService loggingService = new LoggingService();
		CachedBodyHttpServletRequest request = new CachedBodyHttpServletRequest(req);
		loggingService.logRequest(req, loggingService.getBodyRequest(request));
		BaseResponse tokenResponse = new BaseResponse(ImdsErrorEnum.AUTHENTICATION_FAILED.getDescription(),
				ImdsErrorEnum.AUTHENTICATION_FAILED.getCode());
		loggingService.logResponse(req, res, tokenResponse);
		
		
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.setStatus(HttpStatus.OK.value());
		res.getWriter().write(new ObjectMapper().writeValueAsString(tokenResponse));
	}
}
