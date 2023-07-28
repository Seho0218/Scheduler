package com.attendance.scheduler.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

	public static final String[] ENDPOINTS_WHITELIST = {
			"/", "/submit", "/completion",
			"/search/**",
			"/join/**",
			"/login/**",
			"/cert/**",
			"/css/*",
			"/adminLogin/**",
//			"/**"
	};


	public static final String DEFAULT_URL ="/";

	@Bean
	public static PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	private static AuthenticationFailureHandler adminAuthenticationFailureHandler() {
		return new AdminCustomAuthenticationFailureHandler();
	}

	@Bean
	private static AuthenticationFailureHandler teacherAuthenticationFailureHandler() {
		return new TeacherCustomAuthenticationFailureHandler();
	}

	@Bean
	public SecurityFilterChain teacherFilterChain(HttpSecurity http) throws Exception {

		http
				.httpBasic().disable()
				.csrf().disable()
				.authorizeHttpRequests(request -> request
						.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
						.requestMatchers("/manage/").hasRole("TEACHER")
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage(DEFAULT_URL)
						.loginProcessingUrl("/login/Login")
						.defaultSuccessUrl("/manage/class")
						.failureHandler(teacherAuthenticationFailureHandler()))
				.logout(logout -> logout
						.logoutUrl("/logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.logoutSuccessUrl("/"));
		return http.build();
	}

//	@Bean
//	public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
//		http.httpBasic().disable()
//				.csrf().disable()
//				.authorizeHttpRequests(request -> request
//						.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
//						.requestMatchers("/admin/").hasRole("ADMIN")
//						.anyRequest().authenticated())
//				.formLogin(form -> form
//						.loginPage(DEFAULT_URL)
//						.loginProcessingUrl("/login/adminLogin/Login")
//						.usernameParameter("adminId")
//						.defaultSuccessUrl("/admin/teacherList")
//						.failureHandler(adminAuthenticationFailureHandler()))
//				.logout(logout -> logout
//						.logoutUrl("/logout")
//						.invalidateHttpSession(true)
//						.deleteCookies("JSESSIONID")
//						.logoutSuccessUrl("/"));
//		return http.build();
//	}

//	private final CustomAuthenticationProvider customAuthProvider;
//
//	private final AdminCustomAuthenticationFailureHandler
//			adminCustomAuthenticationFailureHandler;
//	@Bean
//	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//		AuthenticationManagerBuilder authenticationManagerBuilder =
//				http.getSharedObject(AuthenticationManagerBuilder.class);
//		authenticationManagerBuilder.authenticationProvider(customAuthProvider);
//		authenticationManagerBuilder.inMemoryAuthentication()
//				.withUser("admin")
//				.password(encoder().encode("123"))
//				.roles("USER");
//		return authenticationManagerBuilder.build();
//	}

}


