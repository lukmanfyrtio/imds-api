package com.idx.imds.api.persistence.dto;

public class GetTokenDto {
	private String userIdPackage;
	private String userSecretPackage;
	private Long maxLifeTime;
	 
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
	public Long getMaxLifeTime() {
		return maxLifeTime;
	}
	public void setMaxLifeTime(Long maxLifeTime) {
		this.maxLifeTime = maxLifeTime;
	}
	
	
	

}
