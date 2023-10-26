package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.StudentInformationDTO;
import com.attendance.scheduler.Repository.jpa.StudentRepository;
import com.attendance.scheduler.Service.ManageStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ManageStudentServiceImpl implements ManageStudentService {

    private final StudentRepository studentRepository;

    @Override
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
                }).collect(Collectors.toList());
    }

    @Override
    public void saveStudentInformation(StudentInformationDTO studentInformationDTO) {
        studentRepository.save(studentInformationDTO.toEntity());
    }

    @Override
    public void deleteStudentInformation(StudentInformationDTO studentInformationDTO) {
        studentRepository.deleteStudentEntityById(studentInformationDTO.getId());
    }
}
