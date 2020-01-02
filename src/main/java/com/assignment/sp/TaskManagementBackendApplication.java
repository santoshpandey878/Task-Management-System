package com.assignment.sp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Application main class
 * Flow start from this class
 */
@SpringBootApplication
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class TaskManagementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementBackendApplication.class, args);
	}

	/**
	 * ModelMapper bean used for DTO conversion
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


}
