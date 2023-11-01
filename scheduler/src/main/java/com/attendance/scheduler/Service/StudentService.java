package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.RegisterStudentDTO;
import com.attendance.scheduler.Dto.StudentInformationDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Optional<StudentInformationDTO> findStudentEntityByStudentName(StudentInformationDTO studentInformationDTO);

    List<StudentInformationDTO> findStudentInformationList(StudentInformationDTO studentInformationDTO);

    void registerStudentInformation(RegisterStudentDTO registerStudentDTO);

    void deleteStudentInformation(StudentInformationDTO studentInformationDTO);


}
