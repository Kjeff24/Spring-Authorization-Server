package com.bexos.authorization_server;

import com.bexos.authorization_server.enums.Role;
import com.bexos.authorization_server.models.User;
import com.bexos.authorization_server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthorizationServerApplication implements CommandLineRunner {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.findByEmailIgnoreCase("test@example.com").orElseGet(
				() -> {
					User user1 = User.builder()
							.fullName("Test User")
							.email("test@example.com")
							.username("test")
							.password(passwordEncoder.encode("password"))
							.role(Role.USER)
							.isEnabled(true)
							.build();

					return userRepository.save(user1);
				});
	}
}
