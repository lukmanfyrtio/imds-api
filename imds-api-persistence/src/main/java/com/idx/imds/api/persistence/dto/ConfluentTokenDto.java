package com.idx.imds.api.persistence.dto;

import java.util.Date;

public class ConfluentTokenDto {

	private String tokenId;
	private String token;
	private Date maxLifeTime;
	private Date expiredTime;
	private Date createdTime;
	private String packageCode;
	private String packageName;
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
	public Date getMaxLifeTime() {
		return maxLifeTime;
	}
	public void setMaxLifeTime(Date maxLifeTime) {
		this.maxLifeTime = maxLifeTime;
	}
	public Date getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getConsumerGroup() {
		return consumerGroup;
	}
	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}
	
	
}
