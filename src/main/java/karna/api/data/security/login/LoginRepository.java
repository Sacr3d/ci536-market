package karna.api.data.security.login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {

	Optional<Login> findByUsername(String username);

}
