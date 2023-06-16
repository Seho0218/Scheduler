package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.LoginTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

public interface TeacherService {

    //교사 회원 가입
    void joinTeacher(JoinTeacherDTO joinTeacherDTO);

    //교사 아이디 중복 검사
    JoinTeacherDTO findDuplicateTeacherId(JoinTeacherDTO joinTeacherDTO);

    //TODO 교사 로그인
    TeacherDTO loginTeacher(LoginTeacherDTO loginTeacherDTO);
}
