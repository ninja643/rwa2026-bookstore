package rs.ac.ni.pmf.rwa.bookstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.ni.pmf.rwa.bookstore.mapper.UserMapper;
import rs.ac.ni.pmf.rwa.bookstore.model.UserDto;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService
{
	private final UserRepository _userRepository;
	private final UserMapper _userMapper;

	@Transactional(readOnly = true)
	public List<UserDto> getAllUsers()
	{
		return _userRepository.findAll().stream()
		                      .map(_userMapper::toDto)
		                      .toList();
	}

	@Transactional(readOnly = true)
	public Optional<UserDto> getUserById(final Long id)
	{
		return _userRepository.findById(id)
		                      .map(_userMapper::toDto);
	}

//	public UserDto createUser(final UserDto user)
//	{
//		final UserDto createdUser = UserDto.builder()
//		                                   .username(user.getUsername())
//		                                   .firstName(user.getFirstName())
//		                                   .lastName(user.getLastName())
//		                                   .email(user.getEmail())
//		                                   .phoneNumber(user.getPhoneNumber())
//		                                   .build();
//
//		_userRepository.;
//		return createdUser;
//	}

	@Transactional
	public Optional<UserDto> updateUser(final Long id, final UserDto user)
	{
		final Optional<UserEntity> optionalExistingUser = _userRepository.findById(id);

		if (optionalExistingUser.isEmpty())
		{
			return Optional.empty();
		}

		final UserEntity existingUser = optionalExistingUser.get();

		existingUser.setUsername(user.getUsername());
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPhone(user.getPhoneNumber());

		final UserEntity savedUser = _userRepository.save(existingUser);

		return Optional.of(savedUser)
		               .map(_userMapper::toDto);
	}

	public void deleteUser(final Long id)
	{
		_userRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<UserDto> getUserByUsername(final String username)
	{
		return _userRepository.findByUsername(username).map(_userMapper::toDto);
	}
}
