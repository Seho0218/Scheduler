package com.attendance.scheduler.student.application;

import com.attendance.scheduler.student.dto.StudentInformationDTO;

import java.util.Optional;

public interface StudentService {

    Optional<StudentInformationDTO> findStudentEntityByStudentName(String studentName);



}
