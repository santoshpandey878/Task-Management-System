package com.assignment.sp.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assignment.sp.services.WebsocketService;

/**
 * Spring aspect class to notify if new task created.
 */
@Aspect
@Component
public class NotifyTaskAspect {

	@Autowired
	private WebsocketService websocketService;

	/**
	 * Pointcut, used to check if createTask method of TaskService is called.
	 * i.e. if new task created.
	 */
	@Pointcut("execution(* com.assignment.sp.services.TaskService.createTask(..))")
	public void createTaskMethodPointcut() {}

	/**
	 * Advice, used to notify websocket to push data to client, when new task created.
	 * @After showing that this advice will be called, when createTask method of TaskService completed the operation.
	 */
	@After("createTaskMethodPointcut()") 
	public void notifyToSendTask() {
		websocketService.pushTasksToUI();
	}

}
