package com.assignment.sp.dao;

import java.util.List;

import com.assignment.sp.entities.Task;

/**
 * Repository to handle all database operation for Task.
 */
public interface TaskRepository extends BaseRepository<Task, Long>{

	/**
	 * Method to get all task of system order by dueDate and priority.
	 * dueDate - Ascending
	 * Assumptions:
	 * priority - Descending (between 0 to 9, highestPriority=9, lowestPriority=0)
	 * @return
	 */
	List<Task> findByOrderByDueDateAscPriorityDesc();

}
