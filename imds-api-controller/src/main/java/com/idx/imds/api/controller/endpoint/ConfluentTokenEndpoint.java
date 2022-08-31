package com.idx.imds.api.controller.endpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idx.imds.api.controller.contract.GetConfluentTokenRequest;
import com.idx.imds.api.controller.contract.GetConfluentTokenResponse;
import com.idx.imds.api.controller.contract.RenewConfluentTokenRequest;
import com.idx.imds.api.core.enums.ImdsErrorEnum;
import com.idx.imds.api.core.exception.ImdsException;
import com.idx.imds.api.persistence.dto.ConfluentTokenDto;
import com.idx.imds.api.service.CustomerUserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("")
public class ConfluentTokenEndpoint {

	private static Logger log = LogManager.getLogger(ConfluentTokenEndpoint.class);
	
	@Autowired
	private CustomerUserService customerUserService;
	
	@PostMapping(value = "/token/create")
	@ResponseBody
	public ResponseEntity<GetConfluentTokenResponse> getToken() {
		GetConfluentTokenResponse response = new GetConfluentTokenResponse();
		try {
			log.info("Try to get token...");

			String username =  SecurityContextHolder.getContext().getAuthentication().getName();

			ConfluentTokenDto token = customerUserService.getToken(username);
			customerUserService.updateToken(token, username);
			response.setToken(token.getToken());
			response.setTokenId(token.getTokenId());
			response.setCode(ImdsErrorEnum.SUCCESS.getCode());
			response.setNamaPaket(token.getPackageName());
			response.setConsumerGroup(token.getConsumerGroup());
		} catch (ImdsException e) {
			response.setCode(e.getImdsErrorEnum().getCode());
			response.setDescription(e.getImdsErrorEnum().getDescription());
		}

		return new ResponseEntity<>(response, HttpStatus.OK); 
	}
	
	
	@PostMapping(value = "/renew/token")
	@ResponseBody
	public ResponseEntity<GetConfluentTokenResponse> renewToken(@RequestBody RenewConfluentTokenRequest request) {
		GetConfluentTokenResponse response = new GetConfluentTokenResponse();
		try {
			log.info("Try to renew token...");

			ConfluentTokenDto token = customerUserService.renewToken(request.getTokenId(), request.getToken());
			response.setToken(token.getToken());
			response.setTokenId(token.getTokenId());
			response.setCode(ImdsErrorEnum.SUCCESS.getCode());
			response.setNamaPaket(token.getPackageName());
			response.setConsumerGroup(token.getConsumerGroup());
		} catch (ImdsException e) {
			response.setCode(e.getImdsErrorEnum().getCode());
			response.setDescription(e.getImdsErrorEnum().getDescription());
		}

		return new ResponseEntity<>(response, HttpStatus.OK); 
	}
	
		
	
}

