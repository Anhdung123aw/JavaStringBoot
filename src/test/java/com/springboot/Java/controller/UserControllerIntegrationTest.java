package com.springboot.Java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.Java.dto.request.UserCreationRequest;
import com.springboot.Java.dto.response.UserResponse;
import com.springboot.Java.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {

	@Container
	static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0");

	@DynamicPropertySource
	static void configureDatasource(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
		registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
		registry.add("spring.datasource.driver-class-name", MY_SQL_CONTAINER::getDriverClassName);

		registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
		registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQLDialect");
	}

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private UserService userService;

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	private UserCreationRequest request;
	private UserResponse userResponse;

	@BeforeEach
	void initData() {
		LocalDate dob = LocalDate.of(1990, 1, 1);

		request = UserCreationRequest.builder().username("DungX").firstName("Le").lastName("Dung").password("12345678")
				.dob(dob).build();

		userResponse = UserResponse.builder().id("dss123sss").username("DungX").firstName("Le").lastName("Dung")
				.dob(dob).build();
	}

	@Test
	void createUser_validRequest_success() throws Exception {
		String content = objectMapper.writeValueAsString(request);

		Mockito.when(userService.createUser(any())).thenReturn(userResponse);

		mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1000))
				.andExpect(MockMvcResultMatchers.jsonPath("$.result.username").value("DungX"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.result.firstName").value("Le"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.result.lastName").value("Dung"));
	}

	@Test
	void createUser_usernameInvalid_fail() throws Exception {
		request.setUsername("Du");

		String content = objectMapper.writeValueAsString(request);

		mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1005))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("INVALID_USER"));
	}
}
