package com.idx.imds.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ConfluentTokenServiceFactory {

	
	@Autowired
	@Qualifier("confluentTokenService")
	private ConfluentTokenService confluentTokenService;
	

	@Autowired
	@Qualifier("confluentTokenSandboxService")
	private ConfluentTokenService confluentTokenSandboxService;
	
	public ConfluentTokenService getTokenService(Boolean isSandbox) {
		if(isSandbox) {
			return confluentTokenSandboxService;
		}else {
			return confluentTokenService;
		}
	}
}
