package rs.ac.ni.pmf.rwa.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.ni.pmf.rwa.bookstore.model.User;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController
{
	private List<User> users = List.of(
			User.builder()
			    .id(1L)
			    .username("admin")
			    .firstName("admin")
			    .lastName("admin")
			    .build(),
			User.builder()
			    .id(2L)
			    .username("user")
			    .firstName("user")
			    .lastName("user")
			    .build()
	);

	@GetMapping
	public List<User> getAllUsers()
	{
		return users;
	}
}
