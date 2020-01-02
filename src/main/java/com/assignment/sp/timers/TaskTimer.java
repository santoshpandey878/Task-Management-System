package com.assignment.sp.timers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.assignment.sp.services.TaskQueueProducerService;

/**
 * Task timer class responsible to start scheduler with fixed rate and delay and create task. 
 */
@Component
@EnableScheduling
@EnableAsync
public class TaskTimer {

	@Autowired
	private TaskQueueProducerService taskQueueProducerService;

	/**
	 * Method to trigger timer/cron process to create task
	 * this process will start in every 10 seconds asynchronously with initial delay of 3 seconds
	 */
	@Async
	@Scheduled(initialDelay = 3000,fixedRate =  10*1000)
	public void taskProducerJob(){
		taskQueueProducerService.produceTask();
	}
}
