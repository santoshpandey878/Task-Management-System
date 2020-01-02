package com.assignment.sp.services;

/**
 * Service layer interface to handle websocket operations
 */
public interface WebsocketService {

	/**
	 * Method to send all task to UI in order by dueDate and priority.
	 * 1.Get all task from database
	 * 2.Convert tasks to DTOs
	 * 3.Send DTOs to client
	 */
	void pushTasksToUI();

}
