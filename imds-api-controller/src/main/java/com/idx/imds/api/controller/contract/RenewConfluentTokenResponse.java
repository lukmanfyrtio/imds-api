package com.idx.imds.api.controller.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RenewConfluentTokenResponse extends BaseResponse {
	@JsonInclude(Include.NON_NULL)
	private String tokenId;
	@JsonInclude(Include.NON_NULL)
	private String token;

	@JsonInclude(Include.NON_NULL)
	private String namaPaket;
	
	@JsonInclude(Include.NON_NULL)
	private String consumerGroup;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNamaPaket() {
		return namaPaket;
	}

	public void setNamaPaket(String namaPaket) {
		this.namaPaket = namaPaket;
	}

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}
	
	

}
