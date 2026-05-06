//package rs.ac.ni.pmf.rwa.bookstore.service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import rs.ac.ni.pmf.rwa.bookstore.model.UserDto;
//import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class UserServiceTest
//{
//	private final UserService _userService = new UserService();
//	private Map<Long, UserDto> _originalUsers;
//
//	@BeforeEach
//	void setUp()
//	{
//		_originalUsers = new HashMap<>(UserRepository.USERS);
//	}
//
//	@AfterEach
//	void tearDown()
//	{
//		UserRepository.USERS.clear();
//		UserRepository.USERS.putAll(_originalUsers);
//	}
//
//	@Test
//	void getAllUsersReturnsAllUsers()
//	{
//		final int expectedSize = UserRepository.USERS.size();
//
//		assertThat(_userService.getAllUsers()).hasSize(expectedSize);
//	}
//
//	@Test
//	void getUserByIdReturnsUserWhenExists()
//	{
//		assertThat(_userService.getUserById(1L)).isPresent();
//	}
//
//	@Test
//	void getUserByIdReturnsEmptyWhenNotExists()
//	{
//		assertThat(_userService.getUserById(999L)).isEmpty();
//	}
//
//	@Test
//	void createUserCreatesUserWithGeneratedId()
//	{
//		final UserDto input = UserDto.builder()
//		                             .username("new-user")
//		                             .firstName("New")
//		                             .lastName("UserDto")
//		                             .email("new.user@example.com")
//		                             .phoneNumber("123456")
//		                             .build();
//
//		final UserDto created = _userService.createUser(input);
//
//		final Long createdId = created.getId();
//
//		assertThat(createdId).isNotNull();
//		assertThat(created.getUsername()).isEqualTo("new-user");
//		assertThat(UserRepository.USERS.get(createdId)).isEqualTo(created);
//	}
//
//	@Test
//	void updateUserReturnsUpdatedUserWhenExists()
//	{
//		final UserDto update = UserDto.builder()
//		                              .username("updated")
//		                              .firstName("Updated")
//		                              .lastName("UserDto")
//		                              .email("updated.user@example.com")
//		                              .phoneNumber("999")
//		                              .build();
//
//		final var result = _userService.updateUser(1L, update);
//
//		assertThat(result).isPresent();
//		assertThat(result.get().getUsername()).isEqualTo("updated");
//		assertThat(UserRepository.USERS.get(1L).getUsername()).isEqualTo("updated");
//	}
//
//	@Test
//	void updateUserReturnsEmptyWhenNotExists()
//	{
//		final UserDto update = UserDto.builder().username("updated").build();
//
//		assertThat(_userService.updateUser(999L, update)).isEmpty();
//	}
//
//	@Test
//	void deleteUserReturnsTrueWhenExists()
//	{
//		assertThat(_userService.deleteUser(1L)).isTrue();
//		assertThat(UserRepository.USERS).doesNotContainKey(1L);
//	}
//
//	@Test
//	void deleteUserReturnsFalseWhenNotExists()
//	{
//		assertThat(_userService.deleteUser(999L)).isFalse();
//	}
//}
