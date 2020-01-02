package com.assignment.sp.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assignment.sp.entities.Task;
import com.assignment.sp.services.TaskQueueConsumerService;

/**
 * Queue configuration class to create queue as a bean and start queue consumer thread.
 *
 */
@Configuration
public class QueueConfig implements ApplicationRunner {

	@Autowired
	private TaskQueueConsumerService taskQueueConsumerService;

	/**
	 * Create BlockingQueue as a spring bean.
	 * So that it can be used anywhere in the application.
	 * @return
	 */
	@Bean
	public BlockingQueue<Task> taskQueue(){
		return new LinkedBlockingQueue<>();
	}

	/**
	 * Method to start thread for TaskQueueConsumerService, once application started.
	 * So that it can consume queue and perform operation on task as soon as task put into queue.
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		new Thread(taskQueueConsumerService).start();

	}

}