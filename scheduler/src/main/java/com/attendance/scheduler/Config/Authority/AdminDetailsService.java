package com.attendance.scheduler.Config.Authority;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {

    AdminVO adminVO = new AdminVO();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,
            InternalAuthenticationServiceException,
            BadCredentialsException {

        log.info("adminId = {}", username);

        if (adminVO.getUsername().equals(username)) {
            return new AdminDetails(adminVO);
        }else if(username.isEmpty()){
            throw new InternalAuthenticationServiceException(username);
        }else{
            throw new UsernameNotFoundException(username);
        }
    }
}
