package com.attendance.scheduler.teacher.application;

import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.dto.StudentInformationDTO;
import com.attendance.scheduler.student.repository.StudentRepository;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.dto.EmailDTO;
import com.attendance.scheduler.teacher.dto.JoinTeacherDTO;
import com.attendance.scheduler.teacher.dto.RegisterStudentDTO;
import com.attendance.scheduler.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
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

    @Override
    @Transactional
    public void registerStudentInformation(RegisterStudentDTO registerStudentDTO) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TeacherEntity byUsernameIs = teacherRepository.findByUsernameIs(auth.getName());

        StudentEntity entity = registerStudentDTO.toEntity();
        entity.setTeacherEntity(byUsernameIs);
        studentRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteStudentInformation(StudentInformationDTO studentInformationDTO) {
        studentRepository.deleteStudentEntityById(studentInformationDTO.getId());
    }

    @Override
    @Transactional
    public List<StudentInformationDTO> findStudentInformationList(StudentInformationDTO studentInformationDTO) {
        return studentRepository.findAll()
                .stream().map(studentEntity -> {
                    StudentInformationDTO informationDTO = new StudentInformationDTO();
                    informationDTO.setId(studentEntity.getId());
                    informationDTO.setStudentName(studentEntity.getStudentName());
                    informationDTO.setStudentAddress(studentEntity.getStudentAddress());
                    informationDTO.setStudentDetailedAddress(studentEntity.getStudentDetailedAddress());
                    informationDTO.setStudentPhoneNumber(studentEntity.getStudentPhoneNumber());
                    informationDTO.setStudentParentPhoneNumber(studentEntity.getStudentParentPhoneNumber());
                    informationDTO.setTeacherName(studentEntity.getTeacherEntity().getUsername());
                    return informationDTO;
                }).toList();
    }
}