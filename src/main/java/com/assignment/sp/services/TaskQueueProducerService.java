package com.assignment.sp.services;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.sp.constant.MessageConstant;
import com.assignment.sp.entities.Task;
import com.assignment.sp.utils.DateUtils;
import com.assignment.sp.utils.MessageUtil;
import com.assignment.sp.utils.ThreadUtil;

/**
 * Service layer class responsible to produce task and put into queue
 */
@Service
public class TaskQueueProducerService {
	private static final Logger logger = LoggerFactory.getLogger(TaskQueueProducerService.class);
	// creating instance of random class to generate priority of task randomly
	private Random random = new Random();

	private final TaskService taskService;
	private final BlockingQueue<Task> taskQueue;
	private final MessageUtil message;

	@Autowired
	public TaskQueueProducerService(TaskService taskService,
			BlockingQueue<Task> taskQueue,
			MessageUtil message) {
		this.taskService = taskService;
		this.taskQueue = taskQueue;
		this.message = message;
	}

	/**
	 * Method to produce task.
	 * 1. Form task
	 * 2. Create task
	 * 3. Put task into queue
	 */
	public void produceTask() {
		// form task
		Task task = formTask();

		// create task
		taskService.createTask(task);
		
		try {
			// put task into queue
			taskQueue.put(task);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Method to form task.
	 * My Assumptions: (I made these assumptions as I don't have details for these fields)
	 * 1.Generating a random number between 0 to 10 (0 including, 10 excluding) to set priority of task
	 * 2.Defined a static title, and adding priority with that. i.e. title = static name + priority
	 * 3.Defined a static description
	 * 4.Setting generated random number as priority of task.
	 * 5.dueDate - I'm generating by adding priority as a day in current date
	 * @return
	 */
	private Task formTask() {
		int priority = random.nextInt(10);
		Task task = new Task();
		task.setTitle(message.get(MessageConstant.TASK_TITLE, priority));
		task.setDescription(message.get(MessageConstant.TASK_DESCRIPTION));
		task.setDueDate(DateUtils.getDueDate(new Date(), priority));
		task.setPriority(priority);
		return task;
	}
}
