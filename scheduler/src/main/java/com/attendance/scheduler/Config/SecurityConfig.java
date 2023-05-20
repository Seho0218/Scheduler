package com.attendance.scheduler.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.
				httpBasic().disable()
				.csrf().disable()
				.authorizeHttpRequests(request -> request
						.requestMatchers("/", "/submit", "/completion",
								"/login","/logout","/admin",
								"/css/**")
						.permitAll()
//						.requestMatchers("/admin/**")
//						.hasAnyRole("ADMIN")
						.anyRequest().authenticated()
				)
					.logout(Customizer.withDefaults());

		return http.build();
	}
}
