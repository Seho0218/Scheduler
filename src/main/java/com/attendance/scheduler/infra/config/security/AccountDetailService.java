package com.attendance.scheduler.infra.config.security;

import com.attendance.scheduler.admin.domain.AdminEntity;
import com.attendance.scheduler.admin.repository.AdminJpaRepository;
import com.attendance.scheduler.infra.config.security.Admin.AdminDetails;
import com.attendance.scheduler.infra.config.security.User.TeacherDetails;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.repository.TeacherJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountDetailService implements UserDetailsService {

    private final AdminJpaRepository adminJpaRepository;
    private final TeacherJpaRepository teacherJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("teacherId = {}", username);

        final TeacherEntity teacherEntity = teacherJpaRepository
                .findByUsernameIs(username);
        if(teacherEntity != null){

            return new TeacherDetails(teacherEntity);

        } else {
            final AdminEntity adminEntity = adminJpaRepository
                    .findByUsernameIs(username);
            if (adminEntity != null) {
                log.info("adminId = {}", username);
                return new AdminDetails(adminEntity);
            }
        }
        throw new UsernameNotFoundException(username);
    }
}