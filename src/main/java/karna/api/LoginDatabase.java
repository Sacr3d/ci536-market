package karna.api;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import karna.api.data.security.login.Login;
import karna.api.data.security.login.LoginRepository;

@Configuration
public class LoginDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoginDatabase.class);
	private static final String INFO_PRELOADING = "Preloading {}";

	private static final String PASSWORD = "password";

	private static final String ROLE_USER = "ROLE_USER";

	private static final String USER1 = "Matt";
	private static final String USER2 = "Tom";
	private static final String USER3 = "Aemon";
	private static final String USER4 = "James";

	@Bean
	CommandLineRunner initLoginDatabase(@Autowired LoginRepository repository,
			@Autowired PasswordEncoder passwordEncoder) {
		return args -> {

			log.info(INFO_PRELOADING, repository.save(Login.builder() //
					.username(USER1) //
					.password(passwordEncoder.encode(PASSWORD)) //
					.roles(Arrays.asList(ROLE_USER)) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(Login.builder() //
					.username(USER2) //
					.password(passwordEncoder.encode(PASSWORD)) //
					.roles(Arrays.asList(ROLE_USER)) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(Login.builder() //
					.username(USER3) //
					.password(passwordEncoder.encode(PASSWORD)) //
					.roles(Arrays.asList(ROLE_USER)) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(Login.builder() //
					.username(USER4) //
					.password(passwordEncoder.encode(PASSWORD)) //
					.roles(Arrays.asList(ROLE_USER)) //
					.build()));

		};
	}
}