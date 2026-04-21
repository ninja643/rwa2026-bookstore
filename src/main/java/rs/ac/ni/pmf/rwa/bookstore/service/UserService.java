package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.model.User;
import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
	public List<User> getAllUsers()
	{
		return UserRepository.USERS.values().stream().toList();
	}

	public Optional<User> getUserById(final Long id)
	{
		return Optional.ofNullable(UserRepository.USERS.get(id));
	}

	public User createUser(final User user)
	{
		final Long id = UserRepository.nextId();
		final User createdUser = User.builder()
		                             .id(id)
		                             .username(user.getUsername())
		                             .firstName(user.getFirstName())
		                             .lastName(user.getLastName())
		                             .email(user.getEmail())
		                             .phoneNumber(user.getPhoneNumber())
		                             .build();

		UserRepository.USERS.put(id, createdUser);
		return createdUser;
	}

	public Optional<User> updateUser(final Long id, final User user)
	{
		if (!UserRepository.USERS.containsKey(id))
		{
			return Optional.empty();
		}

		final User updatedUser = User.builder()
		                             .id(id)
		                             .username(user.getUsername())
		                             .firstName(user.getFirstName())
		                             .lastName(user.getLastName())
		                             .email(user.getEmail())
		                             .phoneNumber(user.getPhoneNumber())
		                             .build();

		UserRepository.USERS.put(id, updatedUser);
		return Optional.of(updatedUser);
	}

	public boolean deleteUser(final Long id)
	{
		return UserRepository.USERS.remove(id) != null;
	}
}
