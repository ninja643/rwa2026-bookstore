package rs.ac.ni.pmf.rwa.bookstore.security;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.PermissionEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.RoleEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails
{
	private final UserEntity _userEntity;

	@Override
	@NullMarked
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		final Set<GrantedAuthority> authorities = new HashSet<>();

		final Set<RoleEntity> roles = _userEntity.getRoles();

		roles.stream()
				.map(role -> "ROLE_" + role.getName())
				.map(SimpleGrantedAuthority::new)
				.forEach(authorities::add);

		roles.stream()
				.flatMap(role -> role.getPermissions().stream())
				.distinct()
				.map(PermissionEntity::getName)
				.map(SimpleGrantedAuthority::new)
				.forEach(authorities::add);

		return authorities;
	}

	@Override
	public @Nullable String getPassword()
	{
		return _userEntity.getPassword();
	}

	@Override
	@NullMarked
	public String getUsername()
	{
		return _userEntity.getUsername();
	}

	public String getFirstName()
	{
		return _userEntity.getFirstName();
	}

	public String getLastName()
	{
		return _userEntity.getLastName();
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
//		return !_userEntity.isExpired();
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
//		return !_userEntity.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
//		return !_userEntity.isCredentialsExpired();
	}

	@Override
	public boolean isEnabled()
	{
		return true;
//		return _userEntity.isEnabled();
	}

	public Set<String> getRoles()
	{
		return _userEntity.getRoles().stream()
				.map(role -> "ROLE_" + role.getName())
				.collect(Collectors.toSet());
	}

	public Set<String> getPermissions()
	{
		return _userEntity.getRoles().stream()
				.flatMap(role -> role.getPermissions().stream())
				.map(PermissionEntity::getName)
				.collect(Collectors.toSet());
	}

	public Long getId()
	{
		return _userEntity.getId();
	}

	public boolean shouldChangePassword()
	{
		return false;
//		return _userEntity.isShouldChangePassword();
	}
}
