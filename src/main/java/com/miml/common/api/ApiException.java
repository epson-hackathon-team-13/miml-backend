package com.miml.common.api;

import com.miml.epson.api.error.EpsonErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;

	public ApiException(String msg) {
		super(msg);    
	}

	public ApiException(EpsonErrorCode errorCode) {
		super(errorCode.getMessage());
	}
}