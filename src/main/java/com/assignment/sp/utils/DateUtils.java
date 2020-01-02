package com.assignment.sp.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Utility class to handle date operations
 */
public class DateUtils {
	
	/**
	 * Method to get dueDate after adding number of days
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDueDate(Date date, int day) {
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localDateTime = localDateTime.plusDays(day);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Method to compare date
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Date date1, Date date2) {
        return date1.compareTo(date2);
	}

}
