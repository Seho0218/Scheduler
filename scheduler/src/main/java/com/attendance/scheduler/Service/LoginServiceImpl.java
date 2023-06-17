package com.attendance.scheduler.Service;

import com.attendance.scheduler.Config.SecurityConfig;
import com.attendance.scheduler.Dto.Teacher.LoginTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Mapper.LoginTeacherMapper;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginTeacherMapper loginTeacherMapper;
    private final SecurityConfig securityConfig;

    @Override
    public TeacherDTO loginTeacher(LoginTeacherDTO loginTeacherDTO) {
        final TeacherEntity byTeacherIdIs = teacherRepository.findByTeacherIdIs(loginTeacherDTO.getTeacherId());
        boolean matches = passwordEncoder.matches(loginTeacherDTO.getTeacherPassword(), byTeacherIdIs.getTeacherPassword());
        if (matches) {
            TeacherDTO teacherDTO = loginTeacherMapper.toTeacherDTO(byTeacherIdIs);
            teacherDTO.setRole(securityConfig.getTeacherRole().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining()));
            return teacherDTO;
        }
        return null;
    }
}
