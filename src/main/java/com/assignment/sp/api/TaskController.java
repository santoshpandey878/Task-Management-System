package com.assignment.sp.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.sp.dto.TaskDto;
import com.assignment.sp.entities.Task;
import com.assignment.sp.services.TaskService;
import com.assignment.sp.utils.DtoConverter;

/**
 * Task rest controller class responsible to handle all client requests and return the response.
 * DTO is used to interact with client.
 */
@RestController
@RequestMapping("rest/tasks")
@CrossOrigin
public class TaskController {

	private final TaskService taskService;
	private final DtoConverter dtoConverter;

	@Autowired
	public TaskController(TaskService taskService,
			DtoConverter dtoConverter) {
		this.taskService = taskService;
		this.dtoConverter = dtoConverter;
	}

	/**
	 * API to fetch all Tasks from system.
	 * All Tasks converted into DTO from entity to sent back to client.
	 * @return 
	 */
	@GetMapping
	public List<TaskDto> getAllTask() {
		List<Task> tasks = taskService.getAllTask();
		return tasks.stream()
				.map(task -> dtoConverter.convertToDto(task))
				.collect(Collectors.toList());
	}

	/**
	 * API to fetch concrete task based on task id
	 * Task converted into DTO to sent client.
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public TaskDto getTaskById(@PathVariable Long id) {
		Task task = taskService.getTaskById(id);
		return dtoConverter.convertToDto(task);
	}

	/**
	 * API to create a task.
	 * Task DTO received from client then it is converted into Task entity.
	 * Return the created task as dto to client
	 * Although, This API is not used in our application, as tasks are creating through cron job scheduler
	 * And client does not have any option to create task from UI.
	 * @param taskDto
	 * @return
	 */
	@PostMapping
	public TaskDto createTask(@Valid @RequestBody TaskDto taskDto) {
		Task task = dtoConverter.convertToEntity(taskDto);
		Task taskCreated = taskService.createTask(task);
		return dtoConverter.convertToDto(taskCreated);
	}

	/**
	 * API to update a task.
	 * Task DTO received from client then it is converted into task entity 
	 * And sent to service layer to perform updation. 
	 * Only Task title, description and priority can be updated.
	 * @param id  (task id)
	 * @param taskDto (DTO having fields to update)
	 */
	@PutMapping("/{id}")
	public void updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
		Task task = dtoConverter.convertToEntity(taskDto);
		taskService.updateTask(id, task);
	}

	/**
	 * API to postpone a task.
	 * Only Task dueDate and remindmeAt can be updated.
	 * remindmeAt date is used to remind on perticular date
	 * @param id
	 * @param taskDto
	 */
	@PutMapping("/postpone/{id}")
	public void postponeTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
		Task task = dtoConverter.convertToEntity(taskDto);
		taskService.postponeTask(id, task);
	}

	/**
	 * API to delete a task.
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
	}

}
