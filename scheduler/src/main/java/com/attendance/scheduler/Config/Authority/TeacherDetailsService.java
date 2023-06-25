package com.attendance.scheduler.Config.Authority;

import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class TeacherDetailsService implements UserDetailsService {
    private final TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String teacherId) throws UsernameNotFoundException {
        final TeacherEntity byTeacherIdIs = teacherRepository.findByTeacherIdIs(teacherId);
        if(byTeacherIdIs != null){
            return new TeacherDetails(byTeacherIdIs);
        }
        return null;
    }
}
