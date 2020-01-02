package com.assignment.sp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.assignment.sp.constant.ApplicationConstant;
import com.assignment.sp.dto.TaskDto;
import com.assignment.sp.utils.DtoConverter;

/**
 * Implementation of service layer interface to handle websocket operations
 */
@Service
public class WebsocketServiceImpl implements WebsocketService {

	private final DtoConverter dtoConverter;
	private final SimpMessagingTemplate simpMessagingTemplate;
	private final TaskService taskService;

	@Autowired
	public WebsocketServiceImpl(TaskService taskService,
			SimpMessagingTemplate simpMessagingTemplate,
			DtoConverter dtoConverter) {
		this.taskService = taskService;
		this.simpMessagingTemplate = simpMessagingTemplate;
		this.dtoConverter = dtoConverter;
	}

	@Override
	public void pushTasksToUI() {
		List<TaskDto> tasksDto = taskService.getAllTask()
				.stream()
				.map(task -> dtoConverter.convertToDto(task))
				.collect(Collectors.toList());

		// send tasks to client
		simpMessagingTemplate.convertAndSend(ApplicationConstant.WEBSOCKET_TOPIC, tasksDto);
	}
}
