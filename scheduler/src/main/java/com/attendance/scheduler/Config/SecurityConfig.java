package com.attendance.scheduler.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
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

	@Bean
	public static PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(1)
	public SecurityFilterChain teacherFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(ENDPOINTS_WHITELIST).permitAll()
						.requestMatchers("/login/Login").permitAll()
						.requestMatchers("/manage/*").hasRole("TEACHER")
						.anyRequest().authenticated()
				)
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login/Login")
				.defaultSuccessUrl("/manage/class")
				.failureHandler(new TeacherCustomAuthenticationFailureHandler())
				.and()
				.logout()
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/");

//				.httpBasic().disable()
//				.csrf().disable();

		log.info("1st Configurer");
		return httpSecurity.build();
	}

	@Bean
	public SecurityFilterChain adminFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/adminLogin/Login").permitAll()
						.requestMatchers("/admin/*").hasRole("ADMIN")
						.anyRequest().authenticated()
				)
				.formLogin()
				.defaultSuccessUrl("/admin/teacherList")
				.failureHandler(new AdminCustomAuthenticationFailureHandler())
				.loginProcessingUrl("/adminLogin/Login")
				.and()
				.logout()
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/");

		log.info("2nd Configurer");
		return httpSecurity.build();
	}
}
