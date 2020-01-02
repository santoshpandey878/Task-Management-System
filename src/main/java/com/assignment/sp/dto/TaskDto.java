package com.assignment.sp.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.assignment.sp.constant.ApplicationConstant;
import com.assignment.sp.constant.StatusType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO class for Task entity used with client interactions.
 */
@JsonInclude(value=Include.NON_NULL)
public class TaskDto {

	private Long id;
	private Date createdAt;
	private Date updatedAt;
	private Date dueDate;
	private Date resolvedAt;
	private Date remindmeAt;
	private String title;
	private String description;
	private int priority;
	private StatusType status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Task title cannot be empty")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Min(value = 0, message = "Priority must be greater than or equal to 0")
	@Max(value = 9, message = "Priority must be less than or equal to 9")
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	@JsonFormat(pattern=ApplicationConstant.API_DATE_FORMAT)
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@JsonFormat(pattern=ApplicationConstant.API_DATE_FORMAT)
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonFormat(pattern=ApplicationConstant.API_DATE_FORMAT)
	@NotNull(message = "Task due date cannot be empty")
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@JsonFormat(pattern=ApplicationConstant.API_DATE_FORMAT)
	public Date getResolvedAt() {
		return resolvedAt;
	}

	public void setResolvedAt(Date resolvedAt) {
		this.resolvedAt = resolvedAt;
	}

	@Future(message = "Remind me date cannot be in past")
	@JsonFormat(pattern=ApplicationConstant.API_DATE_FORMAT)
	public Date getRemindmeAt() {
		return remindmeAt;
	}

	public void setRemindmeAt(Date remindmeAt) {
		this.remindmeAt = remindmeAt;
	}

}
