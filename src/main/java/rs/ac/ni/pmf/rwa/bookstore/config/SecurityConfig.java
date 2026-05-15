package rs.ac.ni.pmf.rwa.bookstore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig
{
	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http)
	{
		log.info("Configuring security filter chain");
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login.html", "/css/**", "/js/**", "/assets/**").permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login.html")
						.loginProcessingUrl("/login")
						.failureUrl("/login.html?error=true")
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login.html?logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
				)
				.build();
	}
}
