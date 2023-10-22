package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void joinTeacher(JoinTeacherDTO joinTeacherDTO) {
        final String encode = passwordEncoder
                .encode(joinTeacherDTO.getPassword());
        joinTeacherDTO.setPassword(encode);
        teacherRepository.save(joinTeacherDTO.toEntity());
    }

    @Override
    public boolean findDuplicateTeacherId(JoinTeacherDTO joinTeacherDTO) {
        return teacherRepository
                .existsByUsername(joinTeacherDTO.getUsername());
    }

    @Override
    public boolean findDuplicateTeacherEmail(JoinTeacherDTO joinTeacherDTO) {
        return teacherRepository
                .existsByEmail(joinTeacherDTO.getEmail());
    }
}