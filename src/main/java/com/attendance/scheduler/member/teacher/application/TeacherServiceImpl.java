package com.attendance.scheduler.member.teacher.application;

import com.attendance.scheduler.course.repository.ClassJpaRepository;
import com.attendance.scheduler.member.student.domain.StudentEntity;
import com.attendance.scheduler.member.student.dto.StudentInformationDTO;
import com.attendance.scheduler.member.student.repository.StudentJpaRepository;
import com.attendance.scheduler.member.student.repository.StudentRepository;
import com.attendance.scheduler.member.teacher.domain.TeacherEntity;
import com.attendance.scheduler.member.teacher.dto.JoinTeacherDTO;
import com.attendance.scheduler.member.teacher.dto.RegisterStudentDTO;
import com.attendance.scheduler.member.teacher.dto.StudentSearchCondition;
import com.attendance.scheduler.member.teacher.repository.TeacherJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherJpaRepository teacherJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final ClassJpaRepository classJpaRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void joinTeacher(JoinTeacherDTO joinTeacherDTO) {
        final String encode = passwordEncoder.encode(joinTeacherDTO.getPassword());
        joinTeacherDTO.setPassword(encode);
        teacherJpaRepository.save(joinTeacherDTO.toEntity());
    }

    @Override
    public boolean findDuplicateTeacherID(JoinTeacherDTO joinTeacherDTO) {
        return teacherJpaRepository.existsByUsername(joinTeacherDTO.getUsername());
    }

    @Override
    public boolean findDuplicateTeacherEmail(JoinTeacherDTO joinTeacherDTO) {
        return teacherJpaRepository.existsByEmail(joinTeacherDTO.getEmail());
    }

    @Override
    @Transactional
    public void registerStudentInformation(RegisterStudentDTO registerStudentDTO) {
        TeacherEntity teacherEntity = teacherJpaRepository.findByUsernameIs(registerStudentDTO.getTeacherEntity());
        registerStudentDTO.setTeacherName(teacherEntity.getTeacherName());

        StudentEntity studentEntity = registerStudentDTO.toEntity();
        studentEntity.setTeacherEntity(teacherEntity);
        studentJpaRepository.save(studentEntity);
    }

    @Override
    @Transactional
    public void deleteStudentInformation(StudentInformationDTO studentInformationDTO) {
        Optional<StudentEntity> studentEntityById = studentJpaRepository.findStudentEntityById(studentInformationDTO.getId());

        studentEntityById.ifPresent(studentEntity -> classJpaRepository.deleteByStudentName(studentEntity.getStudentName()));
        studentJpaRepository.deleteStudentEntityById(studentInformationDTO.getId());
    }

    @Override
    @Transactional
    public Page<StudentInformationDTO> findStudentInformationList(StudentSearchCondition studentSearchCondition, Pageable pageable) {
        return studentRepository.studentInformationDTOList(studentSearchCondition, pageable);
    }
}
