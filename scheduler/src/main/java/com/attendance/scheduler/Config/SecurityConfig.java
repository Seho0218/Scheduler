package com.attendance.scheduler.Config;

import com.attendance.scheduler.Config.Authority.AdminDetailsService;
import com.attendance.scheduler.Config.Authority.TeacherDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

	public static final String[] ENDPOINTS_WHITELIST = {
//			"/", "/submit", "/completion",
//			"/search/**",
//			"/join/**",
//			"/login/**",
//			"/cert/**",
//			"/css/*",
//            "/adminLogin/**",
			"/**"
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

	private final TeacherDetailsService teacherDetailsService;
	private final AdminDetailsService adminDetailsService;

	@Bean
	public UserDetailsService userDetailsService() throws Exception {
		UserDetails admin = User.builder()
				.username("admin")
				.password(encoder().encode("123"))
				.roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(admin);
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder =
				http.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder.userDetailsService(teacherDetailsService);
//		authenticationManagerBuilder.userDetailsService(adminDetailsService);
		return authenticationManagerBuilder.build();
	}

	@Configuration
	public static class teacherConfigurationAdapter{

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
							.logoutSuccessUrl("/")
					);
			return http.build();
		}
	}

//	@Configuration
	public static class adminConfigurationAdapter {

		@Bean
		public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
			http.httpBasic().disable()
					.csrf().disable()
					.authorizeHttpRequests(request -> request
							.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
							.requestMatchers("/admin/").hasRole("ADMIN")
							.anyRequest().authenticated())
					.formLogin(form -> form
							.loginPage(DEFAULT_URL)
							.loginProcessingUrl("/login/adminLogin/Login")
							.defaultSuccessUrl("/admin/teacherList")
							.failureHandler(adminAuthenticationFailureHandler()))
					.logout(logout -> logout
							.logoutUrl("/logout")
							.invalidateHttpSession(true)
							.deleteCookies("JSESSIONID")
							.logoutSuccessUrl("/")
					);
			return http.build();
		}
	}
}


