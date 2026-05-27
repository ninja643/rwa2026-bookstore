package rs.ac.ni.pmf.rwa.bookstore.security;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails
{
	private final Long _id;
	private final String _firstName;
	private final String _lastName;
	private final String _username;
	private final String _password;
	private final Set<String> _roles;
	private final Set<String> _permissions;

	@Override
	@NullMarked
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		final Set<GrantedAuthority> authorities = new HashSet<>();

		_roles.stream()
		      .map(role -> "ROLE_" + role)
		      .map(SimpleGrantedAuthority::new)
		      .forEach(authorities::add);

		_permissions.stream()
		            .map(SimpleGrantedAuthority::new)
		            .forEach(authorities::add);

		return authorities;
	}

	@Override
	public @Nullable String getPassword()
	{
		return _password;
	}

	@Override
	@NullMarked
	public String getUsername()
	{
		return _username;
	}

	public String getFirstName()
	{
		return _firstName;
	}

	public String getLastName()
	{
		return _lastName;
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
		return _roles;
	}

	public Set<String> getPermissions()
	{
		return _permissions;
	}

	public Long getId()
	{
		return _id;
	}

	public boolean shouldChangePassword()
	{
		return false;
//		return _userEntity.isShouldChangePassword();
	}
}
