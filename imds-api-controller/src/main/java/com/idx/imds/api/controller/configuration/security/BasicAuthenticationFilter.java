package com.idx.imds.api.controller.configuration.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.idx.imds.api.controller.contract.BaseResponse;
import com.idx.imds.api.controller.contract.BasicAuthentication;
import com.idx.imds.api.controller.contract.GetConfluentTokenResponse;
import com.idx.imds.api.controller.log.LoggingService;
import com.idx.imds.api.core.enums.ImdsErrorEnum;
import com.idx.imds.api.service.ConfluentTokenService;

public class BasicAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final Logger log = LogManager.getLogger(BasicAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;
	private static final String URL_GET_TOKEN = "/token/confluent";
	private static final String AUTH_HEADER = "Authorization";
	private static final String BASIC_TOKEN_SPLITTER = ":";
	

	public BasicAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl(URL_GET_TOKEN); 
	}

	@Autowired
	private ConfluentTokenService confluentTokenService;
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		LoggingService loggingService = new LoggingService();
		try {
			log.info("Try to authentication...");
			CachedBodyHttpServletRequest request = new CachedBodyHttpServletRequest(req);
			loggingService.logRequest(req, loggingService.getBodyRequest(request));
			BasicAuthentication bAuth = parseHeader( req.getHeader(AUTH_HEADER));
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(bAuth.getUsername(),
					bAuth.getPassword(), new ArrayList<>()));
		} catch (BadCredentialsException e) {
			res.setContentType(MediaType.APPLICATION_JSON_VALUE);
			res.setStatus(HttpStatus.OK.value());
			BaseResponse tokenResponse = new BaseResponse(ImdsErrorEnum.BAD_CREDENTIAL.getDescription(),
					ImdsErrorEnum.BAD_CREDENTIAL.getCode());
			writeBody(res, tokenResponse);
			loggingService.logResponse(req, res, tokenResponse);
			return null;
		} catch (MismatchedInputException e) {
			res.setStatus(HttpStatus.OK.value());
			res.setContentType(MediaType.APPLICATION_JSON_VALUE);
			BaseResponse tokenResponse = new BaseResponse(ImdsErrorEnum.INVALID_PARAMETER.getDescription(),
					ImdsErrorEnum.INVALID_PARAMETER.getCode());
			writeBody(res, tokenResponse);
			loggingService.logResponse(req, res, tokenResponse);
			return null;
		} catch (IOException e) {
			log.error("ERROR", e);
			return null;
		}
	}

	private BasicAuthentication parseHeader(String token) {
		String tokenParsed = new String (Base64.getDecoder().decode(token));
		BasicAuthentication auth = new BasicAuthentication();
		auth.setUsername(tokenParsed.split(BASIC_TOKEN_SPLITTER)[0]);
		auth.setPassword(tokenParsed.split(BASIC_TOKEN_SPLITTER)[1]);
		return auth;
	}

	private void writeBody(HttpServletResponse res, BaseResponse baseResponse) {
		try {
			res.getWriter().write(new ObjectMapper().writeValueAsString(baseResponse));
		} catch (IOException e) {
			log.error("ERROR", e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		CachedBodyHttpServletRequest request = new CachedBodyHttpServletRequest(req);
		
		log.debug("Get Token Confluent");
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.setStatus(HttpStatus.OK.value());
		GetConfluentTokenResponse tokenResponse = new GetConfluentTokenResponse();
		tokenResponse.setCode(ImdsErrorEnum.SUCCESS.getCode());
		tokenResponse.setDescription(ImdsErrorEnum.SUCCESS.getDescription());

		LoggingService loggingService = new LoggingService();
		loggingService.logResponse(req, res, tokenResponse);
		res.getWriter().write(new ObjectMapper().writeValueAsString(tokenResponse));

	}

}
