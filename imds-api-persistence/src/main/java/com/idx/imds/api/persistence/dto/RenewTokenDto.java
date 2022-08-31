package com.idx.imds.api.persistence.dto;

public class RenewTokenDto {
	private String tokenId;
	private String token;
	private String userIdPackage;
	private String userSecretPackage;
	private Long expiredMilisecond;
	
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
	public String getUserIdPackage() {
		return userIdPackage;
	}
	public void setUserIdPackage(String userIdPackage) {
		this.userIdPackage = userIdPackage;
	}
	public String getUserSecretPackage() {
		return userSecretPackage;
	}
	public void setUserSecretPackage(String userSecretPackage) {
		this.userSecretPackage = userSecretPackage;
	}
	public Long getExpiredMilisecond() {
		return expiredMilisecond;
	}
	public void setExpiredMilisecond(Long expiredMilisecond) {
		this.expiredMilisecond = expiredMilisecond;
	}
	
	

}
