package com.attendance.scheduler.Config;

import com.attendance.scheduler.Config.Authority.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
//			"/", "/submit", "/completion", // 제출 완료 페이지
//			"/search/**", // 조회 및 수정
//			"/join/**",
//			"/login/**",
//			"/cert/**",
//			"/css/*",
//            "/adminLogin/*"

			"/**"
	};


	public static final String DEFAULT_URL ="/";

	@Bean
	public static PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
			throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationFailureHandler adminAuthenticationFailureHandler() {
		return new AdminCustomAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Order(1)
	@Configuration
	@RequiredArgsConstructor
	public static class TeacherConfigurationAdapter {
		private final CustomAuthenticationFailureHandler
				authenticationFailureHandler;

		@Bean
		public SecurityFilterChain teacherFilterChain(HttpSecurity http) throws Exception {
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

					.logout(logout -> logout
							.logoutUrl("/logout")
							.invalidateHttpSession(true)
							.deleteCookies("JSESSIONID")
							.logoutSuccessUrl("/"));
			return http.build();
		}

		@Order(2)
		@Configuration
		@RequiredArgsConstructor
		public static class AdminConfigurationAdapter {

			private final CustomAuthenticationProvider customAuthProvider;

			private final AdminCustomAuthenticationFailureHandler
					adminCustomAuthenticationFailureHandler;
			@Bean
			public AuthenticationManager authManager(HttpSecurity http) throws Exception {
				AuthenticationManagerBuilder authenticationManagerBuilder =
						http.getSharedObject(AuthenticationManagerBuilder.class);
				authenticationManagerBuilder.authenticationProvider(customAuthProvider);
				authenticationManagerBuilder.inMemoryAuthentication()
						.withUser("admin")
						.password(encoder().encode("123"))
						.roles("USER");
				return authenticationManagerBuilder.build();
			}


			@Bean
			public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
				http.httpBasic().disable()
						.csrf().disable()
						.authorizeHttpRequests(request -> request
								.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
								.requestMatchers("/admin/*").hasRole("ADMIN")
								.anyRequest().authenticated())

						.formLogin(form -> form
								.loginPage(DEFAULT_URL) // 인증 필요한 페이지 접근시 이동페이지
								.loginProcessingUrl("/login/adminLogin/Login")
								.usernameParameter("adminId")
								.defaultSuccessUrl("/admin/teacherList")
								.failureHandler(adminCustomAuthenticationFailureHandler))

						.logout(logout -> logout
								.logoutUrl("/logout")
								.invalidateHttpSession(true)
								.deleteCookies("JSESSIONID")
								.logoutSuccessUrl("/"));
				return http.build();
			}
		}

	}
}

