package com.machado.task_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.machado.task_service.dto.TaskDTO;
import com.machado.task_service.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
/**
 * junit to know we need test containers to work
 */
@Testcontainers
@AutoConfigureMockMvc
class TaskServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Using containers for testing, this helps us to verify that if there was a new DB
	 * then using this entity template, our code works fine on every new instance.
	 * Make sure to enable "spring.jpa.hibernate.ddl-auto=update", so as to allow spring to manage
	 * any DDL script changes
	 */
	@Container
	static PostgreSQLContainer<?> pgvector = new PostgreSQLContainer<>("pgvector/pgvector:pg16");

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private TaskRepository taskRepository;


	@Test
	void contextLoads() {
		System.out.println("Loaded Beans: " + Arrays.toString(applicationContext.getBeanDefinitionNames()));
	}

	/**
	 *
	 * @param dynamicPropertyRegistry
	 * This is required as we cant use the default application properties datasource details.
	 * This is for the new container spinup which is going to be different all the times.
	 * Use the container and extract details efficiently and store those value needed to
	 * properly run the DB and connect to it
	 */
	@DynamicPropertySource
	static void setPostgresProperty(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.datasource.url", pgvector::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", pgvector::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", pgvector::getPassword);
		dynamicPropertyRegistry.add("spring.datasource.driverClassName", pgvector::getDriverClassName);
	}


	@Test
	void shouldCreateTaskSuccessfully() throws Exception {
		TaskDTO taskDTO = getDummyTaskDto();
		String taskDtoString = objectMapper.writeValueAsString(taskDTO);
		// mock to perform requestBuilder dummy post request controller
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/task")
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskDtoString)
		).andExpect(status().isCreated());

		Assertions.assertEquals(taskRepository.findAll().size(),1);
	}

	private TaskDTO getDummyTaskDto() {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setOngoing(true);
		taskDTO.setDescription("something");
		taskDTO.setTitle("something");
		taskDTO.setEndDate(LocalDateTime.now().plusDays(30));
		taskDTO.setStartDate(LocalDateTime.now());
		return taskDTO;
	}
}
