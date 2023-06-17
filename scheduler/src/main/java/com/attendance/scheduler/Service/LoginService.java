package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.LoginTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

public interface LoginService {

    //교사 로그인
    TeacherDTO loginTeacher(LoginTeacherDTO loginTeacherDTO);
}
