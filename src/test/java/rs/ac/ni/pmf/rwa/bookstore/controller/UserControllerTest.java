package rs.ac.ni.pmf.rwa.bookstore.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import rs.ac.ni.pmf.rwa.bookstore.TestData;
import rs.ac.ni.pmf.rwa.bookstore.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest
{
	@Autowired
	MockMvc _mockMvc;

	@MockitoBean
	UserService _userService;

	@Test
	void shouldReturnAllUsers() throws Exception
	{
		when(_userService.getAllUsers()).thenReturn(List.of(
				TestData.USERS.ADMIN,
				TestData.USERS.USER
		));

		_mockMvc.perform(get("/api/v1/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].username").value(TestData.USERS.ADMIN.getUsername()))
				.andExpect(jsonPath("$[1].username").value(TestData.USERS.USER.getUsername()));
	}

	@Test
	void shouldReturnUserById() throws Exception
	{
		when(_userService.getUserById(1L)).thenReturn(Optional.of(TestData.USERS.ADMIN));

		_mockMvc.perform(get("/api/v1/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value(TestData.USERS.ADMIN.getUsername()));

		verify(_userService).getUserById(1L);
	}

	@Test
	void shouldReturn404WhenUserNotFound() throws Exception
	{
		_mockMvc.perform(get("/api/v1/users/999"))
				.andExpect(status().isNotFound());
	}
}
