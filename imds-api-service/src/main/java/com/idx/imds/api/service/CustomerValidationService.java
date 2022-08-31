package com.idx.imds.api.service;

import com.idx.imds.api.persistence.entity.CustomerUser;

public interface CustomerValidationService {

	void validate(CustomerUser user);

}
