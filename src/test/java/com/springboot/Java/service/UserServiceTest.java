package com.springboot.Java.service;

import com.springboot.Java.dto.request.UserCreationRequest;
import com.springboot.Java.entity.User;
import com.springboot.Java.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
// @TestPropertySource("/application.properties")
class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockitoBean
	private UserRepository userRepository;

	private UserCreationRequest request;
	private User user;

	@BeforeEach
	void initData() {
		LocalDate dob = LocalDate.of(1990, 1, 1);

		request = UserCreationRequest.builder().username("DungX").firstName("Le").lastName("Dung").password("12345678")
				.dob(dob).build();

		user = User.builder().id(1).username("DungX").firstName("Le").lastName("Dung").dob(dob).build();
	}

	@Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // WHEN
        var response = userService.createUser(request);

        // THEN
        Assertions.assertEquals("1", response.getId());
        Assertions.assertEquals("DungX", response.getUsername());
    }
}
