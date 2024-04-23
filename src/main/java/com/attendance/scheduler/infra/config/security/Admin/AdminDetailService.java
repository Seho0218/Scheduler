package com.attendance.scheduler.infra.config.security.Admin;

import com.attendance.scheduler.admin.domain.AdminEntity;
import com.attendance.scheduler.admin.repository.AdminJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminDetailService implements UserDetailsService {

    @Autowired
    private AdminJpaRepository adminJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("adminId = {}", username);

        final AdminEntity adminEntity = adminJpaRepository
                .findByUsernameIs(username);

        if(adminEntity != null){
            return new AdminDetails(adminEntity);
        }

        throw new UsernameNotFoundException(username);
    }
}