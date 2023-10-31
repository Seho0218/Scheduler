package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.StudentInformationDTO;

import java.util.List;

public interface StudentService {

    List<StudentInformationDTO> findStudentEntityByStudentName(StudentInformationDTO studentInformationDTO);

    List<StudentInformationDTO> findStudentInformationList(StudentInformationDTO studentInformationDTO);

    void registerStudentInformation(StudentInformationDTO studentInformationDTO);

    void deleteStudentInformation(StudentInformationDTO studentInformationDTO);


}
