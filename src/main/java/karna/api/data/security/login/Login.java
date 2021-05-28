package karna.api.data.security.login;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "logins")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Login implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9135143477208066241L;

	private @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "id", unique = true, nullable = false) Long id;

	private @NotEmpty String password;

	private @ElementCollection(fetch = FetchType.EAGER) @Builder.Default List<String> roles = new ArrayList<>();

	private @NotEmpty String username;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
