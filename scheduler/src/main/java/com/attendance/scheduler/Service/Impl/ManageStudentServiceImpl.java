package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.StudentInformationDTO;
import com.attendance.scheduler.Entity.StudentEntity;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.StudentRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.ManageStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManageStudentServiceImpl implements ManageStudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public List<StudentInformationDTO> findStudentEntityByStudentName(StudentInformationDTO studentInformationDTO) {
        return studentRepository.findStudentEntityByStudentName(studentInformationDTO.getStudentName())
                .stream().map(studentEntity -> {
                    StudentInformationDTO informationDTO = new StudentInformationDTO();
                    informationDTO.setStudentName(studentEntity.getStudentName());
                    return informationDTO;
                }).toList();
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
                    return informationDTO;
                }).toList();
    }

    @Override
    @Transactional
    public void registerStudentInformation(StudentInformationDTO studentInformationDTO) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TeacherEntity byUsernameIs = teacherRepository.findByUsernameIs(auth.getName());

        StudentEntity entity = studentInformationDTO.toEntity();
        entity.setTeacherEntity(byUsernameIs);
        byUsernameIs.getStudentEntityList().add(entity);
        studentRepository.save(entity);
    }

    @Override
    public void deleteStudentInformation(StudentInformationDTO studentInformationDTO) {
        studentRepository.deleteStudentEntityById(studentInformationDTO.getId());
    }
}
