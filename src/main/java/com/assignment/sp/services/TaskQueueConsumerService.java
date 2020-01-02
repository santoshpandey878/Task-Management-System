package com.assignment.sp.services;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.assignment.sp.constant.StatusType;
import com.assignment.sp.entities.Task;
import com.assignment.sp.utils.DateUtils;
import com.assignment.sp.utils.ThreadUtil;

/**
 * Service layer class responsible to consume task
 */
@Service
public class TaskQueueConsumerService implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(TaskQueueConsumerService.class);
	private final BlockingQueue<Task> taskQueue;
	private final TaskService taskService;

	@Autowired
	public TaskQueueConsumerService(@Lazy TaskService taskService,
			@Lazy BlockingQueue<Task> taskQueue) {
		this.taskService = taskService;
		this.taskQueue = taskQueue;
	}

	@Override
	public void run() {
		// once this thread started loop will start and as soon as task available in queue, 
		// it will take the head of queue and process it
		while(true) {
			try {
				// get the front task from queue and also remove it from queue
				Task task = taskQueue.take();
				
				switch(task.getStatus()) {
				case IN_PROCESS_QUEUE:
					// if task status is IN_PROCESS_QUEUE then perform the task operations
					logger.info("Task id: {} with status {} is going to perform operation", task.getId(), task.getStatus());
					ThreadUtil.start(() -> performTaskOperation(task));
					break;
				case POSTPONED:
					// if task status is POSTPONED then compare the dueDate with currentDate
					// if dueDate passed, then perform the operation else put again into queue 

					// My Assumptions: (below are my assumptions)
					// if task is postponed, then it will wait until the due date and if it is over 
					// it will perform the operation and mark it resolved.
					// I don't have details for these operations like what I have to perform for these cases
					// so I have done above assumptions.
					
					if(DateUtils.compareDate(task.getDueDate(), new Date()) < 0) {
						logger.info("Task id: {} with status {} is going to perform operation", task.getId(), task.getStatus());
						ThreadUtil.start(() -> performTaskOperation(task));
					} else {
						taskQueue.put(task);
						logger.info("Task id: {} with status {} is postponed", task.getId(), task.getStatus());
					}
					break;
				default:
					break;
				}
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}

			// sleep for 20 seconds
			// this is only used to make delay
			ThreadUtil.sleep(20*1000);
		}
	}

	/**
	 * Method to perform task operations
	 * @param task
	 */
	private void performTaskOperation(Task task) {
		// update status of task to EXECUTING, so that client cannot postponed it
		task.setStatus(StatusType.EXECUTING);
		taskService.updateTask(task);

		// Perform required operation here, when status of task is executing
		// this will be a critical section, 
		// so here we can use try catch block and if any exception occured, we can mark task status as FAILED
		// I'm just putting sleep for 20 seconds, as I don't have details like what operations have to perform
		ThreadUtil.sleep(20*1000);

		// once task operations completed mark it resolved.
		task.setStatus(StatusType.RESOLVED);
		task.setResolvedAt(new Date());
		taskService.updateTask(task);
	}

}
