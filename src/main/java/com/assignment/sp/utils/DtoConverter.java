package com.assignment.sp.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assignment.sp.dto.TaskDto;
import com.assignment.sp.entities.Task;

/**
 * Utility class responsible for dto conversion
 */
@Component
public class DtoConverter {

	@Autowired
	public ModelMapper modelMapper;

	/**
	 * Method to convert Task entity to DTO
	 * @param task
	 * @return
	 */
	public TaskDto convertToDto(Task task) {
		return modelMapper.map(task, TaskDto.class);
	}

	/**
	 * Method to convert DTO to entity Task
	 * @param taskDto
	 * @return
	 */
	public Task convertToEntity(TaskDto taskDto) {
		return modelMapper.map(taskDto, Task.class);
	}
}