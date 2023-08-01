package com.attendance.scheduler.Config.Authority;

import com.attendance.scheduler.Entity.AdminEntity;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.AdminRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TeacherDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("teacherId = {}", username);

        final AdminEntity byAdminIdIs = adminRepository.findByAdminIdIs(username);
        final TeacherEntity byTeacherIdIs = teacherRepository.findByTeacherIdIs(username);

        if(byTeacherIdIs != null){
            return new TeacherDetails(byTeacherIdIs);
        }

        if(byAdminIdIs != null){
            return new AdminDetails(byAdminIdIs);
        }
        throw new UsernameNotFoundException(username);
    }
}
