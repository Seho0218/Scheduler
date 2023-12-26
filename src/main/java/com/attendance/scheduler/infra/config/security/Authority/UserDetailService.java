package com.attendance.scheduler.infra.config.security.Authority;

import com.attendance.scheduler.member.admin.domain.AdminEntity;
import com.attendance.scheduler.member.admin.repository.AdminRepository;
import com.attendance.scheduler.member.teacher.domain.TeacherEntity;
import com.attendance.scheduler.member.teacher.repository.TeacherJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final TeacherJpaRepository teacherJpaRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("teacherId = {}", username);

        final TeacherEntity teacherEntity = teacherJpaRepository
                .findByUsernameIs(username);
        if(teacherEntity != null){
            return new TeacherDetails(teacherEntity);
        }

        final AdminEntity adminEntity = adminRepository
                .findByUsernameIs(username);
        if(adminEntity != null){
            return new AdminDetails(adminEntity);
        }

        throw new UsernameNotFoundException(username);
    }
}