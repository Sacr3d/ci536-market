package karna.api;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import karna.api.data.user.User;
import karna.api.data.user.UserRepository;

@Configuration
class UserDatabase {

	private static final Logger log = LoggerFactory.getLogger(UserDatabase.class);
	private static final String INFO_PRELOADING = "Preloading {}";

	private static final String EMAIL_END = "@uni.ac.uk";

	private static final String USER_ADDRESS = "Brighton Uni";
	private static final Date USER_DOB = new Date();

	private static final String USER_PHONE = "xxxxxxxxxxx";
	private static final String USER1 = "Matt";
	private static final String USER2 = "Tom";
	private static final String USER3 = "Aemon";
	private static final String USER4 = "James";

	@Bean
	CommandLineRunner initUserDatabase(@Autowired UserRepository repository) {
		return args -> {
			log.info(INFO_PRELOADING, repository.save(User.builder() //
					.owner(USER1) //
					.userName(USER1) //
					.userDOB(USER_DOB) //
					.userAdress(USER_ADDRESS) //
					.userEmail(USER1 + EMAIL_END) //
					.userContactNumber(USER_PHONE) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(User.builder() //
					.owner(USER2) //
					.userName(USER2) //
					.userDOB(USER_DOB) //
					.userAdress(USER_ADDRESS) //
					.userEmail(USER2 + EMAIL_END) //
					.userContactNumber(USER_PHONE) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(User.builder() //
					.owner(USER3) //
					.userName(USER3) //
					.userDOB(USER_DOB) //
					.userAdress(USER_ADDRESS) //
					.userEmail(USER3 + EMAIL_END) //
					.userContactNumber(USER_PHONE) //
					.build()));

			log.info(INFO_PRELOADING, repository.save(User.builder() //
					.owner(USER4) //
					.userName(USER4) //
					.userDOB(USER_DOB) //
					.userAdress(USER_ADDRESS) //
					.userEmail(USER4 + EMAIL_END) //
					.userContactNumber(USER_PHONE) //
					.build()));

		};
	}

}