package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.EmailDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

import java.util.List;

public interface TeacherService {

    //교사 회원 가입
    void joinTeacher(JoinTeacherDTO joinTeacherDTO);

    //교사 회원 삭제
    void deleteTeacher(TeacherDTO teacherDTO);

    //교사 아이디 중복 검사
    boolean findDuplicateTeacherID(JoinTeacherDTO joinTeacherDTO);

    //교사 이메일 중복 검사
    boolean findDuplicateTeacherEmail(JoinTeacherDTO joinTeacherDTO);

    List<EmailDTO> findTeacherEmailByID(EmailDTO emailDTO);

}
