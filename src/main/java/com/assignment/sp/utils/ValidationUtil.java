package com.assignment.sp.utils;

import java.util.LinkedList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * utility class for managing json request fields validation
 */
public class ValidationUtil {
	
	/**
	 * Method to pull error messages for json request
	 * @param errors
	 * @return
	 */
	public static List<String> getErrorMessages(Errors errors) {
		List<String> errorMessages = new LinkedList<>();
		for (ObjectError objectError : errors.getAllErrors()) {
			errorMessages.add(objectError.getDefaultMessage());
		}
		return errorMessages;
	}

}
