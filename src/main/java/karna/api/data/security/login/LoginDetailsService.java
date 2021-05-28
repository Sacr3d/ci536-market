package karna.api.data.security.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class LoginDetailsService implements UserDetailsService {

	private LoginRepository repository;

	public LoginDetailsService(LoginRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
	}
}
