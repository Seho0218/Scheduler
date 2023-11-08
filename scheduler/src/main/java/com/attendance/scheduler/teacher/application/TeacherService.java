package com.attendance.scheduler.teacher.application;

import com.attendance.scheduler.student.dto.StudentInformationDTO;
import com.attendance.scheduler.teacher.dto.JoinTeacherDTO;
import com.attendance.scheduler.teacher.dto.RegisterStudentDTO;

import java.util.List;

public interface TeacherService {

    //교사 회원 가입
    void joinTeacher(JoinTeacherDTO joinTeacherDTO);

    //교사 아이디 중복 검사
    boolean findDuplicateTeacherID(JoinTeacherDTO joinTeacherDTO);

    //교사 이메일 중복 검사
    boolean findDuplicateTeacherEmail(JoinTeacherDTO joinTeacherDTO);


    void registerStudentInformation(RegisterStudentDTO registerStudentDTO);

    void deleteStudentInformation(StudentInformationDTO studentInformationDTO);

    List<StudentInformationDTO> findStudentInformationList();
}
