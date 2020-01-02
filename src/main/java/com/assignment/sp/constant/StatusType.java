package com.assignment.sp.constant;

/**
 * Enumerated values for task status
 */
public enum StatusType {
	IN_PROCESS_QUEUE("IN_PROCESS_QUEUE"),
	EXECUTING("EXECUTING"),
	RESOLVED("RESOLVED"),
	FAILED("FAILED"), 
	POSTPONED("POSTPONED");

	private String status;

	private StatusType(String status) {
		this.status = status;
	}

	public String value(){
		return this.status;
	}
}
