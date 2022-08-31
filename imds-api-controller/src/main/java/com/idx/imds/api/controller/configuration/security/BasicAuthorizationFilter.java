package com.idx.imds.api.controller.configuration.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idx.imds.api.controller.contract.BaseResponse;
import com.idx.imds.api.controller.log.LoggingService;
import static com.idx.imds.api.core.constants.BasicAuthConstant.TOKEN_PREFIX;
import static com.idx.imds.api.core.constants.BasicAuthConstant.TOKEN_PARAM;

public class BasicAuthorizationFilter extends BasicAuthenticationFilter {
	private final Logger log = LogManager.getLogger(BasicAuthorizationFilter.class);

	public BasicAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Autowired
	private CustomBasicAuthenticator customBasicAuthenticator;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		log.info("Try to check authorization...");
		CachedBodyHttpServletRequest request = new CachedBodyHttpServletRequest(req);
		String tokenBody = getTokenHeaders(request);
		if (tokenBody == null || !tokenBody.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, res);
			return;
		}
		try {
			UsernamePasswordAuthenticationToken authentication = customBasicAuthenticator.getAuthentication(tokenBody);
			getAuthenticationManager().authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, res);
		} catch (Exception e) {
			chain.doFilter(request, res);
//		} catch (TokenExpiredException e) {
//			BaseResponse response = new BaseResponse(DynatransRestErrorCodeEnum.TOKEN_EXPIRED.getMessage(),
//					DynatransRestErrorCodeEnum.TOKEN_EXPIRED.getCode());
//
//			LoggingService loggingService = new LoggingService();
//			loggingService.logRequest(request, loggingService.getBodyRequest(request));
//			loggingService.logResponse(req, res, response);
//
//			res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//			res.setStatus(HttpStatus.OK.value());
//			res.getWriter().write(new ObjectMapper().writeValueAsString(response));
//		} catch (UserAlreadyLogoutException e) {
//
//			BaseResponse response = new BaseResponse(DynatransRestErrorCodeEnum.ALREADY_LOGOUT.getMessage(),
//					DynatransRestErrorCodeEnum.ALREADY_LOGOUT.getCode());
//
//			LoggingService loggingService = new LoggingService();
//			loggingService.logRequest(request, loggingService.getBodyRequest(request));
//			loggingService.logResponse(req, res, response);
//
//			res.setContentType(MediaType.APPLICATION_JSON_VALUE);
//			res.setStatus(HttpStatus.OK.value());
//			res.getWriter().write(new ObjectMapper().writeValueAsString(response));
		}
	}

	private String getTokenHeaders(HttpServletRequest req) {
		return req.getHeader(TOKEN_PARAM);

	}

}
