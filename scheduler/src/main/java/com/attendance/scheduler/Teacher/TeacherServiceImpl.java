package com.attendance.scheduler.Teacher;

import com.attendance.scheduler.Infra.Dto.EmailDTO;
import com.attendance.scheduler.Teacher.Dto.JoinTeacherDTO;
import com.attendance.scheduler.Teacher.Dto.TeacherDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

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
    public void deleteTeacher(TeacherDTO teacherDTO) {
        teacherRepository.deleteByUsernameIs(teacherDTO.getUsername());
    }

    @Override
    public boolean findDuplicateTeacherID(JoinTeacherDTO joinTeacherDTO) {
        return teacherRepository
                .existsByUsername(joinTeacherDTO.getUsername());
    }

    @Override
    public boolean findDuplicateTeacherEmail(JoinTeacherDTO joinTeacherDTO) {
        return teacherRepository
                .existsByEmail(joinTeacherDTO.getEmail());
    }

    @Override
    public Optional<EmailDTO> findTeacherEmailByUsername(EmailDTO emailDTO) {
        TeacherEntity teacherEntity = teacherRepository
                .findByUsernameIs(emailDTO.getUsername());

        return Optional.ofNullable(EmailDTO.builder()
                .username(teacherEntity.getUsername())
                .email(teacherEntity.getEmail())
                .build());
    }
}