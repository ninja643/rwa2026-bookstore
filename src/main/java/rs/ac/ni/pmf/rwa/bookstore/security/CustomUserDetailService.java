package rs.ac.ni.pmf.rwa.bookstore.security;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService
{
	private final UserRepository _userRepository;

	@Override
	@NonNull
	public UserDetails loadUserByUsername(final @NonNull String username) throws UsernameNotFoundException
	{
		return _userRepository.findByUsername(username)
				.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}
}
