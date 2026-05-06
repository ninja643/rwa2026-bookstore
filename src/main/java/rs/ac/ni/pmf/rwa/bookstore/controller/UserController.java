package rs.ac.ni.pmf.rwa.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.ni.pmf.rwa.bookstore.model.User;
import rs.ac.ni.pmf.rwa.bookstore.model.UserDto;
import rs.ac.ni.pmf.rwa.bookstore.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController
{
	private final UserService _userService;

	@GetMapping
	public List<UserDto> getAllUsers()
	{
		return _userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public UserDto getUserById(@PathVariable final Long id)
	{
		return _userService.getUserById(id)
		                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserDto not found"));
	}

//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public UserDto createUser(@RequestBody final UserDto user)
//	{
//		return _userService.createUser(user);
//	}

	@PutMapping("/{id}")
	public UserDto updateUser(@PathVariable final Long id,
	                          @RequestBody @Valid final UserDto user)
	{
		return _userService.updateUser(id, user)
		                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserDto not found"));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable final Long id)
	{
		_userService.deleteUser(id);
	}

	@GetMapping("/username/{username}")
	public UserDto getUserByUsername(@PathVariable final String username)
	{
		return _userService.getUserByUsername(username)
		                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserDto not found"));
	}
}
