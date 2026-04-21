package rs.ac.ni.pmf.rwa.bookstore.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rs.ac.ni.pmf.rwa.bookstore.model.User;
import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest
{
	private final UserService userService = new UserService();
	private Map<Long, User> originalUsers;

	@BeforeEach
	void setUp()
	{
		originalUsers = new HashMap<>(UserRepository.USERS);
	}

	@AfterEach
	void tearDown()
	{
		UserRepository.USERS.clear();
		UserRepository.USERS.putAll(originalUsers);
	}

	@Test
	void getAllUsersReturnsAllUsers()
	{
		final int expectedSize = UserRepository.USERS.size();

		assertThat(userService.getAllUsers()).hasSize(expectedSize);
	}

	@Test
	void getUserByIdReturnsUserWhenExists()
	{
		assertThat(userService.getUserById(1L)).isPresent();
	}

	@Test
	void getUserByIdReturnsEmptyWhenNotExists()
	{
		assertThat(userService.getUserById(999L)).isEmpty();
	}

	@Test
	void createUserCreatesUserWithGeneratedId()
	{
		final User input = User.builder()
		                       .username("new-user")
		                       .firstName("New")
		                       .lastName("User")
		                       .email("new.user@example.com")
		                       .phoneNumber("123456")
		                       .build();

		final User created = userService.createUser(input);

		assertThat(created.getId()).isNotNull();
		assertThat(created.getUsername()).isEqualTo("new-user");
		assertThat(UserRepository.USERS.get(created.getId())).isEqualTo(created);
	}

	@Test
	void updateUserReturnsUpdatedUserWhenExists()
	{
		final User update = User.builder()
		                        .username("updated")
		                        .firstName("Updated")
		                        .lastName("User")
		                        .email("updated.user@example.com")
		                        .phoneNumber("999")
		                        .build();

		final var result = userService.updateUser(1L, update);

		assertThat(result).isPresent();
		assertThat(result.get().getUsername()).isEqualTo("updated");
		assertThat(UserRepository.USERS.get(1L).getUsername()).isEqualTo("updated");
	}

	@Test
	void updateUserReturnsEmptyWhenNotExists()
	{
		final User update = User.builder().username("updated").build();

		assertThat(userService.updateUser(999L, update)).isEmpty();
	}

	@Test
	void deleteUserReturnsTrueWhenExists()
	{
		assertThat(userService.deleteUser(1L)).isTrue();
		assertThat(UserRepository.USERS).doesNotContainKey(1L);
	}

	@Test
	void deleteUserReturnsFalseWhenNotExists()
	{
		assertThat(userService.deleteUser(999L)).isFalse();
	}
}
