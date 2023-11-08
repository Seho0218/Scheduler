package com.attendance.scheduler.teacher.application;

import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.dto.StudentInformationDTO;
import com.attendance.scheduler.student.repository.StudentRepository;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.dto.JoinTeacherDTO;
import com.attendance.scheduler.teacher.dto.RegisterStudentDTO;
import com.attendance.scheduler.teacher.repository.TeacherRepository;
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
    @Transactional
    public void registerStudentInformation(RegisterStudentDTO registerStudentDTO) {
        TeacherEntity teacherEntity = teacherRepository.findByUsernameIs(registerStudentDTO.getTeacherEntity());
        StudentEntity studentEntity = registerStudentDTO.toEntity();
        studentEntity.setTeacherEntity(teacherEntity);
        studentRepository.save(studentEntity);
    }

    @Override
    @Transactional
    public void deleteStudentInformation(StudentInformationDTO studentInformationDTO) {
        studentRepository.deleteStudentEntityById(studentInformationDTO.getId());
    }

    @Override
    @Transactional
    public List<StudentInformationDTO> findStudentInformationList() {
        return studentRepository.findAll()
                .stream().map(studentEntity -> {
                    System.out.println("1 = " + 1);
                    StudentInformationDTO informationDTO = new StudentInformationDTO();
                    informationDTO.setId(studentEntity.getId());
                    informationDTO.setStudentName(studentEntity.getStudentName());
                    informationDTO.setStudentAddress(studentEntity.getStudentAddress());
                    informationDTO.setStudentDetailedAddress(studentEntity.getStudentDetailedAddress());
                    informationDTO.setStudentPhoneNumber(studentEntity.getStudentPhoneNumber());
                    informationDTO.setStudentParentPhoneNumber(studentEntity.getStudentParentPhoneNumber());
                    informationDTO.setTeacherName(studentEntity.getTeacherEntity().getUsername());
                    System.out.println("2 = " + 2);
                    return informationDTO;
                }).toList();
    }
}