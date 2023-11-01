package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.RegisterStudentDTO;
import com.attendance.scheduler.Dto.StudentInformationDTO;
import com.attendance.scheduler.Entity.StudentEntity;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.StudentRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public Optional<StudentInformationDTO> findStudentEntityByStudentName(StudentInformationDTO studentInformationDTO) {
        return studentRepository.findStudentEntityByStudentNameIs(studentInformationDTO.getStudentName())
                .map(studentEntity -> {
                    StudentInformationDTO informationDTO = new StudentInformationDTO();
                    informationDTO.setId(studentEntity.getId());
                    informationDTO.setStudentName(studentEntity.getStudentName());
                    return informationDTO;
                });
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
}
