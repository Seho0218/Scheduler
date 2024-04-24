package com.attendance.scheduler.infra.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] ENDPOINTS_WHITELIST = {
            "/", "/submit", "/completion",
            "/class/**",
            "/board/**",
            "/join/**",
            "/cert/**",
            "/help/**",
            "/comment/**",
            "/css/**",
            "/js/**"
    };

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/admin/**","/manage/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**")
                        .hasAuthority("ADMIN")
                        .requestMatchers("/manage/**")
                        .hasAnyAuthority("ADMIN", "TEACHER")
                        .anyRequest().authenticated())

                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .defaultSuccessUrl("/manage/class", true)
                        .failureHandler(customAuthenticationFailureHandler)
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                )

                .logout(httpSecurityFormLogoutConfigurer -> httpSecurityFormLogoutConfigurer
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/"))

                .sessionManagement(sessionManagement -> sessionManagement
                        .invalidSessionUrl("/login")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/login"));

        return httpSecurity.build();
    }
}
