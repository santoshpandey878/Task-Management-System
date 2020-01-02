package com.assignment.sp.utils;

import org.apache.commons.lang3.ThreadUtils;

/**
 * Utility class for threads
 */
public class ThreadUtil extends ThreadUtils{

	/**
	 * Method to start thread 
	 * @param runnable
	 */
	public static void start(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();
	}

	/**
	 * Method to sleep/pause thread execution based on time in milliseconds
	 * @param millis
	 */
	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			//Do nothing
		}
	}

}
