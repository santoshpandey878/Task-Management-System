package com.assignment.sp.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class to load information/error message from 
 * message.properties file
 */
@Component
public class MessageUtil {

	@Autowired
	MessageSource messageSource;

	/**
	 * Method to find error message based on id
	 * @param messageId
	 * @return
	 */
	public String get(String messageId){
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(messageId, null, locale);
	}

	/**
	 * Method to get formatted error messages
	 * @param messageId
	 * @param args
	 * @return
	 */
	public String get(String messageId, Object ...args){
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(messageId, args, locale);
	}
}
