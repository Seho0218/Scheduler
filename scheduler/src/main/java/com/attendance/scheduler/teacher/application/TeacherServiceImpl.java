package com.attendance.scheduler.teacher.application;

import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.dto.StudentInformationDTO;
import com.attendance.scheduler.student.repository.StudentJpaRepository;
import com.attendance.scheduler.student.repository.StudentRepository;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.dto.JoinTeacherDTO;
import com.attendance.scheduler.teacher.dto.RegisterStudentDTO;
import com.attendance.scheduler.teacher.repository.TeacherJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherJpaRepository teacherJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
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
        studentJpaRepository.deleteStudentEntityById(studentInformationDTO.getId());
    }

    @Override
    @Transactional
    public List<StudentInformationDTO> findStudentInformationList() {
        return studentRepository.studentInformationDTOList();
    }
}
