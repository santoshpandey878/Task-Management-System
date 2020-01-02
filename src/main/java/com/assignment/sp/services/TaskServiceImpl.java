package com.assignment.sp.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.sp.constant.MessageConstant;
import com.assignment.sp.constant.StatusType;
import com.assignment.sp.dao.TaskRepository;
import com.assignment.sp.entities.Task;
import com.assignment.sp.exceptions.ResourceNotFoundException;
import com.assignment.sp.exceptions.ServiceException;
import com.assignment.sp.utils.DateUtils;
import com.assignment.sp.utils.MessageUtil;

/**
 * Implementation of task service layer interface to handle business logic of task operations
 */
@Service
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final BlockingQueue<Task> taskQueue;
	private final MessageUtil message;

	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository,
			BlockingQueue<Task> taskQueue,
			MessageUtil message) {
		this.taskRepository = taskRepository;
		this.taskQueue = taskQueue;
		this.message = message;
	}

	@Override
	public List<Task> getAllTask() {
		return taskRepository.findByOrderByDueDateAscPriorityDesc();
	}

	@Override
	public Task getTaskById(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(message.get(MessageConstant.NOT_FOUND, id)));
	}

	@Transactional
	@Override
	public Task createTask(Task task) {
		task.setCreatedAt(new Date());
		task.setStatus(StatusType.IN_PROCESS_QUEUE);
		return taskRepository.save(task);
	}

	@Transactional
	@Override
	public void updateTask(Long id, Task task) {
		Task existingTask = getTaskById(id);
		existingTask.setTitle(task.getTitle());
		existingTask.setDescription(task.getDescription());
		existingTask.setPriority(task.getPriority());
		existingTask.setUpdatedAt(new Date());
		taskRepository.save(existingTask);

		// update task present in queue
		updateTaskPresentInQueue(existingTask);
	}

	@Override
	public void postponeTask(Long id, Task task) {
		// validate task
		validateTask(task);

		Task existingTask = getTaskById(id);
		existingTask.setDueDate(task.getDueDate());
		existingTask.setRemindmeAt(task.getRemindmeAt());
		existingTask.setStatus(StatusType.POSTPONED);
		existingTask.setUpdatedAt(new Date());
		taskRepository.save(existingTask);

		// update task present in queue
		updateTaskPresentInQueue(existingTask);

	}

	@Transactional
	@Override
	public void updateTask(Task task) {
		// validate task before update, if task is not already deleted from client
		if(null != getTaskById(task.getId())) {
			taskRepository.save(task);
		}
	}

	@Transactional
	@Override
	public void deleteTask(Long id) {
		Task existingTask = getTaskById(id);
		taskQueue.remove(existingTask);
		taskRepository.deleteById(id);

	}

	/**
	 * Method to validate task before it postponed
	 * @param task
	 */
	private void validateTask(Task task) {
		// if remindmeAt date is greater than dueDate then throw exception.
		// My Assumptions: 
		// dueDate will be the final date to finish a task i.e. task must be resolved before due date
		// and remindme date should be before dueDate
		if(null != task.getRemindmeAt() && DateUtils.compareDate(task.getRemindmeAt(), task.getDueDate()) > 0) {
			throw new ServiceException(message.get(MessageConstant.REMINDME_GREATER_THAN_DUEDATE));
		}

		// if status of task is EXECUTING, RESOLVED or FAILED then throw exception.
		// My assumptions:  
		// no meaning of postponed if task is already completed and removed from queue.
		// Task can be only postponed if status is IN_PROCESS_QUEUE or POSTPONED itself.
		if(task.getStatus() == StatusType.EXECUTING ||
				task.getStatus() == StatusType.RESOLVED ||
				task.getStatus() == StatusType.FAILED) {
			throw new ServiceException(message.get(MessageConstant.TASK_STATUS, task.getStatus()));
		}
	}

	/**
	 * Method to update task present in queue to make it in sync, if task gets updated/postponed
	 * @param newTask
	 */
	private void updateTaskPresentInQueue(Task newTask) {
		Iterator<Task> it=taskQueue.iterator();
		while(it.hasNext()){
			Task task=it.next();
			if(task.getId().equals(newTask.getId())){
				task.setTitle(newTask.getTitle());
				task.setDescription(newTask.getDescription());
				task.setDueDate(newTask.getDueDate());
				task.setPriority(newTask.getPriority());
				task.setRemindmeAt(newTask.getRemindmeAt());
				task.setUpdatedAt(newTask.getUpdatedAt());
				task.setStatus(newTask.getStatus());
			}
		}
	}

}
