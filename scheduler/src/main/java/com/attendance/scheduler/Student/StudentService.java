package com.attendance.scheduler.Student;

import com.attendance.scheduler.Student.Dto.StudentInformationDTO;
import com.attendance.scheduler.Teacher.Dto.RegisterStudentDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Optional<StudentInformationDTO> findStudentEntityByStudentName(StudentInformationDTO studentInformationDTO);

    List<StudentInformationDTO> findStudentInformationList(StudentInformationDTO studentInformationDTO);

    void registerStudentInformation(RegisterStudentDTO registerStudentDTO);

    void deleteStudentInformation(StudentInformationDTO studentInformationDTO);


}
