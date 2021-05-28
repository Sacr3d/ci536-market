package karna.api.data.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtProperties {

	private String secretKey = "secret";

	// validity in milliseconds
	private long validityInMs = 3600000; // 1h
}
