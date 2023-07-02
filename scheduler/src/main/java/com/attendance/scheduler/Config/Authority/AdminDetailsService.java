package com.attendance.scheduler.Config.Authority;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


//@Service
@RequiredArgsConstructor
@Transactional
public class AdminDetailsService implements UserDetailsService {

    AdminVO adminVO = new AdminVO();

    @Override
    public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {

        if(adminVO.getId() != null){
            return new AdminDetails(adminVO);
        }
        return null;
    }
}
