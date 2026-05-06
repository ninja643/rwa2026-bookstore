package rs.ac.ni.pmf.rwa.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.ni.pmf.rwa.bookstore.model.User;
import rs.ac.ni.pmf.rwa.bookstore.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController
{
	private final UserService _userService;

	@GetMapping
	public List<User> getAllUsers()
	{
		return _userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable final Long id)
	{
		return _userService.getUserById(id)
		                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody final User user)
	{
		return _userService.createUser(user);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable final Long id, @RequestBody final User user)
	{
		return _userService.updateUser(id, user)
		                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable final Long id)
	{
		if (!_userService.deleteUser(id))
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}
	}
}
