package com.assignment.sp.exceptions;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.assignment.sp.utils.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * A sample class for adding error information in the response
 */
public class RestErrorInfo {

	@JsonSerialize(using = DateSerializer.class,nullsUsing=DateSerializer.class)
	private Date timeStamp;

	private Integer code;
	private String status;
	private List<String> errorMessages = new LinkedList<>();

	public RestErrorInfo(HttpStatus status, String errorMessage) {
		this.timeStamp = new Date();
		this.code = status.value();
		this.status = status.name();
		this.errorMessages.add(errorMessage);
	}

	public RestErrorInfo(HttpStatus status, List<String> errorMessages) {
		this.timeStamp = new Date();
		this.code = status.value();
		this.status = status.name();
		this.errorMessages = errorMessages;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
}
