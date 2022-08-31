package com.idx.imds.api.core.exception;

import com.idx.imds.api.core.enums.ImdsErrorEnum;

public class ImdsException extends RuntimeException{

	private static final long serialVersionUID = -2572063530753290975L;
	private ImdsErrorEnum imdsErrorEnum;
	public ImdsException(ImdsErrorEnum imdsErrorEnum) {
		super(imdsErrorEnum.getDescription());
		this.imdsErrorEnum = imdsErrorEnum;
	}
	public ImdsException(String message, Throwable cause) {
		super(message, cause);
	}
	public ImdsException(String message) {
		super(message);
	}
	public ImdsException(Throwable cause) {
		super(cause);
	}
	public ImdsErrorEnum getImdsErrorEnum() {
		return imdsErrorEnum;
	}
	public void setImdsErrorEnum(ImdsErrorEnum imdsErrorEnum) {
		this.imdsErrorEnum = imdsErrorEnum;
	}
	
	
}
