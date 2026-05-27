package rs.ac.ni.pmf.rwa.bookstore.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.RoleEntity;
import rs.ac.ni.pmf.rwa.bookstore.model.entity.UserEntity;
import rs.ac.ni.pmf.rwa.bookstore.repository.RoleRepository;
import rs.ac.ni.pmf.rwa.bookstore.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
@NullMarked
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner
{
	private final UserRepository _userRepository;
	private final RoleRepository _roleRepository;
	private final PasswordEncoder _passwordEncoder;

	@Override
	public void run(final String... args)
	{
		if (!_userRepository.existsByUsername("admin"))
		{
			log.info("Creating default user...");
			final Set<RoleEntity> roles = new HashSet<>(_roleRepository.findAll());
			final UserEntity user = UserEntity.builder()
			                                  .username("admin")
			                                  .password(_passwordEncoder.encode("admin.123"))
			                                  .firstName("Marko")
			                                  .lastName("Milošević")
			                                  .email("marko.milosevic@pmf.edu.rs")
			                                  .roles(roles)
			                                  .build();
			_userRepository.save(user);
			log.info("Admin user created");
		}
	}
}
