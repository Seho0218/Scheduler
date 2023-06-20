package com.attendance.scheduler.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	public static final String[] ENDPOINTS_WHITELIST = {
//			"/", "/submit", "/completion", // 제출 완료 페이지
//			"/search/**", // 조회 및 수정
//			"/join/**",
//			"/login/**",
//			"/css/*" // css
			"/**"
	};
	@Autowired
	private CustomAuthenticationFailureHandler authenticationFailureHandler;
	public static final String DEFAULT_URL ="/";

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.
			httpBasic().disable()
			.csrf().disable()
				.authorizeHttpRequests(request -> request
					.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
					.requestMatchers("/manage/*").hasRole("TEACHER")
					.anyRequest().authenticated())

				.formLogin(form -> form
						.loginPage(DEFAULT_URL) // 인증 필요한 페이지 접근시 이동페이지
						.loginProcessingUrl("/login/Login")
						.defaultSuccessUrl("/manage/class")
						.usernameParameter("id")
						.failureHandler(authenticationFailureHandler)
				)

				.logout(logout -> logout
						.logoutUrl("/logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.logoutSuccessUrl("/"));
		return http.build();
	}

	@Bean
	public PasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	//여러개의 권한을 가질 수 있기에 이렇게 함.
	public Collection<? extends GrantedAuthority> getTeacherRole() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("TEACHER"));
		return authorities;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}
}

