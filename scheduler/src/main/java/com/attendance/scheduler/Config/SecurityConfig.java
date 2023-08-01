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

	@Bean
	public static PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(1)
	public SecurityFilterChain teacherFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
//				.securityMatcher("/manage/*")
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/manage/*").hasRole("TEACHER")
						.anyRequest().permitAll()
				)
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/manage/class")
				.failureHandler(new TeacherCustomAuthenticationFailureHandler())
				.loginProcessingUrl("/login")
				.and()
				.logout()
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/")
				.and()

				.httpBasic().disable()
				.csrf().disable();

		log.info("1st Configurer");
		return httpSecurity.build();
	}

	@Bean
	public SecurityFilterChain adminFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.securityMatcher("/admin/*")
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/admin/*").hasRole("ADMIN")
						.anyRequest().permitAll()
				)
				.formLogin()
				.loginPage("/adminLogin")
				.defaultSuccessUrl("/admin/teacherList")
				.failureHandler(new AdminCustomAuthenticationFailureHandler())
				.loginProcessingUrl("/admin/adminLogin/Login")
				.and()
				.logout()
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/")
				.and()

				.httpBasic().disable()
				.csrf().disable();

		log.info("2nd Configurer");
		return httpSecurity.build();
	}
}
