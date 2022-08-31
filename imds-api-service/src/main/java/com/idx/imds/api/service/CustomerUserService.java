package com.idx.imds.api.service;

import java.util.List;

import com.dtt.generic.service.services.GeneralService;
import com.idx.imds.api.persistence.dto.ConfluentTokenDto;
import com.idx.imds.api.persistence.entity.CustomerUser;

public interface CustomerUserService extends GeneralService<CustomerUser, Long>{

	CustomerUser getByUserName(String username);
	
	ConfluentTokenDto getToken(String username);
	
	void updateToken(ConfluentTokenDto confluentToken, String username);

	ConfluentTokenDto renewToken(String tokenId, String token);

	CustomerUser getByTokenIdAndToken(String tokenId, String token);

	List<String> getTopic(String tokenId, String token);

}
