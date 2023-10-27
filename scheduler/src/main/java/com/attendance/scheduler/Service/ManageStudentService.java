package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.StudentInformationDTO;

import java.util.List;

public interface ManageStudentService {

    List<StudentInformationDTO> findStudentInformationList(StudentInformationDTO studentInformationDTO);

    void registerStudentInformation(StudentInformationDTO studentInformationDTO);

    void deleteStudentInformation(StudentInformationDTO studentInformationDTO);


}
