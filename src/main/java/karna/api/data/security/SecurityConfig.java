package karna.api.data.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import karna.api.data.security.jwt.JwtSecurityConfigurer;
import karna.api.data.security.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	// @formatter:off
    http
      .httpBasic().disable() //
      .csrf().disable() //
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
      .and() //
      .authorizeRequests() //
      .antMatchers("/auth/signin").permitAll() //  
      .antMatchers("/items/recent*").permitAll() //
      .antMatchers("/items/trending*").permitAll() //
      .antMatchers("/items/category*").permitAll() //
      .antMatchers("/items/all").permitAll() //
      .antMatchers("/").permitAll() //
      .antMatchers("/*.html").permitAll() //
      .antMatchers("/css/**").permitAll() //
      .antMatchers("/js/**").permitAll() //
      .antMatchers("/img/**").permitAll() //
      .antMatchers("/*.ico").permitAll() //
      .anyRequest().authenticated() //
      .and() //
      .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    // @formatter:on
	}

}
