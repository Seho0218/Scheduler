package com.attendance.scheduler.Config.Authority;

import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class TeacherDetailsService implements UserDetailsService {
    private final TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("teacherId = {}", username);
        final TeacherEntity byTeacherIdIs = teacherRepository.findByTeacherIdIs(username);
        if(byTeacherIdIs == null){
            return null;
        }
        return new TeacherDetails(byTeacherIdIs);
    }
}
