package rs.ac.ni.pmf.rwa.bookstore.security;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.PermissionEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.RoleEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService
{
	private final UserRepository _userRepository;

	@Override
	@NonNull
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final @NonNull String username) throws UsernameNotFoundException
	{
		final UserEntity userEntity = _userRepository
				.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		final Set<RoleEntity> roleEntities = userEntity.getRoles();

		final Set<String> roles = roleEntities
				.stream()
				.map(RoleEntity::getName)
				.collect(Collectors.toSet());

		final Set<String> permissions = roleEntities
				.stream()
				.flatMap(role -> role.getPermissions().stream())
				.map(PermissionEntity::getName)
				.collect(Collectors.toSet());

		return new CustomUserDetails(
				userEntity.getId(),
				userEntity.getFirstName(),
				userEntity.getLastName(),
				userEntity.getUsername(),
				userEntity.getPassword(),
				roles,
				permissions);
	}
}
