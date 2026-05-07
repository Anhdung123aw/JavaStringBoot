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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
// @TestPropertySource("/application.properties")
public class UserControllerTest {

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
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
				.andExpect(MockMvcResultMatchers.jsonPath("result.id").value("dss123sss"));
	}
	void createUser_usernameInvalid_fail() throws Exception {
		String content = objectMapper.writeValueAsString(request);

		Mockito.when(userService.createUser(any())).thenReturn(userResponse);

		mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1005))
				.andExpect(MockMvcResultMatchers.jsonPath("message").value("INVALID_USER"));
	}
}
