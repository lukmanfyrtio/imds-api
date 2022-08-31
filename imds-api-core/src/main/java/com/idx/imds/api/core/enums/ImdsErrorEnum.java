package com.idx.imds.api.core.enums;

public enum ImdsErrorEnum {
	SUCCESS							("00", "Success"),

	
	INVALID_PARAMETER				("40", "Invalid parameter"),
	BAD_CREDENTIAL					("41", "Bad Credential"),
	USER_HAS_EXPIRED 				("42", "User has expired"),
	AUTHENTICATION_FAILED			("43", "Authentication failed"), 
	NOT_HAVE_PACKAGE				("44", "User not have package"), 
	NOTHING_TOKEN_GENERATED 		("50", "Please create token first"), 
	INVALID_TOKEN  					("51", "Invalid token"), 
	
	;
	
	private String code;
	private String description;
	
	private ImdsErrorEnum(String code, String description){
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static ImdsErrorEnum getByCode(String code){
		for(ImdsErrorEnum en : ImdsErrorEnum.values()){
			if(en.getCode().equals(code)){
				return en;
			}
		}
		return null;
	}
}
