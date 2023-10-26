package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.StudentInformationDTO;

import java.util.List;

public interface ManageStudentService {

    List<StudentInformationDTO> findStudentInformationList(StudentInformationDTO studentInformationDTO);

    void saveStudentInformation(StudentInformationDTO studentInformationDTO);

    void deleteStudentInformation(StudentInformationDTO studentInformationDTO);

}
