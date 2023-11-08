package com.attendance.scheduler.student.application;

import com.attendance.scheduler.student.dto.StudentInformationDTO;
import com.attendance.scheduler.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;


    @Override
    public Optional<StudentInformationDTO> findStudentEntityByStudentName(String studentName) {
        return studentRepository.findStudentEntityByStudentNameIs(studentName)
                .map(studentEntity -> {
                    StudentInformationDTO informationDTO = new StudentInformationDTO();
                    informationDTO.setId(studentEntity.getId());
                    informationDTO.setStudentName(studentEntity.getStudentName());
                    return informationDTO;
                });
    }
}
