package com.attendance.scheduler.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

	private final CustomAuthenticationFailureHandler authenticationFailureHandler;

	public static final String[] ENDPOINTS_WHITELIST = {
			"/", "/submit", "/completion", // 제출 완료 페이지
			"/search/**", // 조회 및 수정
			"/join/**",
			"/login/**",
			"/cert/**",
			"/css/*" // css
//			"/**"
	};


	public static final String DEFAULT_URL ="/";

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.authorizeHttpRequests(request -> request
				.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
				.requestMatchers("/manage/*").hasRole("TEACHER")
				.anyRequest().authenticated())

			.formLogin(form -> form
				.loginPage(DEFAULT_URL) // 인증 필요한 페이지 접근시 이동페이지
				.loginProcessingUrl("/login/Login")
				.usernameParameter("teacherId")
				.defaultSuccessUrl("/manage/class")
				.failureHandler(authenticationFailureHandler))

			.formLogin(form -> form
				.loginPage(DEFAULT_URL) // 인증 필요한 페이지 접근시 이동페이지
				.loginProcessingUrl("/admin/Login")
				.usernameParameter("adminId")
				.defaultSuccessUrl("/manage/class")
				.failureHandler(authenticationFailureHandler))

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
	public UserDetailsService userDetailsService(){
		UserDetails admin = User.builder()
				.username("admin")
				.password(encodePwd().encode("Root123!@#"))
				.roles("TEACHER", "ADMIN")
				.build();
		return new InMemoryUserDetailsManager(admin);
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

