package com.attendance.scheduler.teacher.application;

import com.attendance.scheduler.student.dto.StudentInformationDTO;
import com.attendance.scheduler.teacher.dto.JoinTeacherDTO;
import com.attendance.scheduler.teacher.dto.RegisterStudentDTO;
import com.attendance.scheduler.teacher.dto.StudentSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TeacherService {

    //교사 회원 가입
    void joinTeacher(JoinTeacherDTO joinTeacherDTO);

    //교사 아이디 중복 검사
    boolean findDuplicateTeacherID(JoinTeacherDTO joinTeacherDTO);

    //교사 이메일 중복 검사
    boolean findDuplicateTeacherEmail(JoinTeacherDTO joinTeacherDTO);


    void registerStudentInformation(RegisterStudentDTO registerStudentDTO);

    void deleteStudentInformation(StudentInformationDTO studentInformationDTO);

    Page<StudentInformationDTO> findStudentInformationList(StudentSearchCondition studentSearchCondition, Pageable pageable);
}
