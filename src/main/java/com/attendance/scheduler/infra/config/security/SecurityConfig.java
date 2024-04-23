package com.attendance.scheduler.infra.config.security;

import com.attendance.scheduler.infra.config.security.Admin.AdminAuthenticationFailureHandler;
import com.attendance.scheduler.infra.config.security.Admin.AdminDetailService;
import com.attendance.scheduler.infra.config.security.User.TeacherAuthenticationFailureHandler;
import com.attendance.scheduler.infra.config.security.User.TeacherDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Order(1)
    public static class AdminFilter {

        @Bean
        public UserDetailsService adminUserDetailsService() {
            return new AdminDetailService();
        }

        @Bean
        public DaoAuthenticationProvider adminAuthenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(adminUserDetailsService());
            provider.setPasswordEncoder(passwordEncoder());
            return provider;
        }

        @Bean
        public SecurityFilterChain adminFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {

            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

            httpSecurity
                    .authenticationProvider(adminAuthenticationProvider())
                    .securityMatcher("/admin/**")
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(mvcMatcherBuilder.pattern("/admin/**"))
                            .hasRole("ADMIN")
                            .anyRequest().authenticated())
                    .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                            .loginPage("/adminLogin")
                            .loginProcessingUrl("/adminLogin/login")
                            .defaultSuccessUrl("/manage/class", true)
                            .failureHandler(new AdminAuthenticationFailureHandler())
                            .and())
                    .logout(httpSecurityFormLogoutConfigurer -> httpSecurityFormLogoutConfigurer
                            .logoutUrl("/logout")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .logoutSuccessUrl("/"))
                    .sessionManagement(sessionManagement -> sessionManagement
                            .invalidSessionUrl("/adminLogin")
                            .maximumSessions(1)
                            .maxSessionsPreventsLogin(true)
                            .expiredUrl("/adminLogin"))
                    .csrf(AbstractHttpConfigurer::disable);

            log.info("1st Configurer");
            return httpSecurity.build();
        }

        @Order(2)
        public static class teacherFilter {

            @Bean
            public UserDetailsService teacherDetailsService() {
                return new TeacherDetailService();
            }

            @Bean
            public DaoAuthenticationProvider teacherAuthenticationProvider(){
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(teacherDetailsService());
                provider.setPasswordEncoder(passwordEncoder());
                return provider;
            }

            @Bean
            public SecurityFilterChain teacherFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {
                MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
                httpSecurity
                        .authenticationProvider(teacherAuthenticationProvider())
                        .securityMatcher("/manage/**")
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers(mvcMatcherBuilder.pattern("/manage/**"))
                                .hasAnyRole("TEACHER", "ADMIN")
                                .anyRequest().authenticated())
                        .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                                .loginPage("/login")
                                .loginProcessingUrl("/login/login")
                                .defaultSuccessUrl("/manage/class", true)
                                .failureHandler(new TeacherAuthenticationFailureHandler()))
                        .logout(httpSecurityFormLogoutConfigurer -> httpSecurityFormLogoutConfigurer
                                .logoutUrl("/logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .logoutSuccessUrl("/"))
                        .sessionManagement(sessionManagement -> sessionManagement
                                .invalidSessionUrl("/login")
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                                .expiredUrl("/login"))
                        .csrf(AbstractHttpConfigurer::disable);
                log.info("2st Configurer");
                return httpSecurity.build();
            }
        }
    }
}
