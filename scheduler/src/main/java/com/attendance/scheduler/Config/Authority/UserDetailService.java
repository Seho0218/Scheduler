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

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("teacherId = {}", username);

        final Optional<TeacherEntity> optionalTeacherEntity = teacherRepository.findByUsernameIs(username);
        if(optionalTeacherEntity.isPresent()){
            return new TeacherDetails(optionalTeacherEntity.get());
        }

        final Optional<AdminEntity> optionalAdminEntity = adminRepository.findByUsernameIs(username);
        if(optionalAdminEntity.isPresent()){
            return new AdminDetails(optionalAdminEntity.get());
        }
        throw new UsernameNotFoundException(username);
    }
}
