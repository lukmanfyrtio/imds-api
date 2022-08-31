package com.idx.imds.api.controller.contract;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class BaseResponse {
	public static final String SUCCESS_STATUS = "Success";
	public static final String ERROR_STATUS = "Error";
	private static final String ERROR_CODE = "99";
	private static final String SUCCESS_CODE = "00";
	
	private String description;
	private String code;
	private List<String> errors;
	private String message; 
	
	public BaseResponse() {
		this.code = SUCCESS_CODE;
		this.description = SUCCESS_STATUS;
	}
	public BaseResponse(String description, String code) {
		super();
		this.description = description;
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	@JsonInclude(Include.NON_NULL)
	public void setDescription(String description) {
		this.description = description;
	}
	@JsonInclude(Include.NON_NULL)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public static String getErrorCode() {
		return ERROR_CODE;
	}
	public static String getSuccessCode() {
		return SUCCESS_CODE;
	}
    @JsonInclude(Include.NON_NULL)
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.description = ERROR_STATUS;
		this.errors = errors;
	}
    @JsonInclude(Include.NON_NULL)
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
