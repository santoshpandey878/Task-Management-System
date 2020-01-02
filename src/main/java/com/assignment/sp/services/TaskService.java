package com.assignment.sp.services;

import java.util.List;

import com.assignment.sp.entities.Task;

/**
 * Task service layer interface to handle business logic of task operations
 */
public interface TaskService {

	/**
	 * Method to fetch all tasks present in system
	 * @return
	 */
	List<Task> getAllTask();

	/**
	 * Method to fetch concrete task based on id
	 * @param id
	 * @return
	 */
	Task getTaskById(Long id);

	/**
	 * Method to create task
	 * @param task
	 * @return
	 */
	Task createTask(Task task);

	/**
	 * Method to update task based on id
	 * updatable fields: title, description, priority
	 * @param id
	 * @param task
	 */
	void updateTask(Long id, Task task);

	/**
	 * Method to update task
	 * @param task
	 */
	void updateTask(Task task);

	/**
	 * Method to delete task
	 * @param id
	 */
	void deleteTask(Long id);

	/**
	 * Method to postpone task
	 * Only dueDate and remindmeAt fields are updated
	 * @param id
	 * @param task
	 */
	void postponeTask(Long id, Task task);

}
