package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;

public interface JoinService {

    //교사 회원 가입
    void joinTeacher(JoinTeacherDTO joinTeacherDTO);

    //교사 아이디 중복 검사
    boolean findDuplicateTeacherId(JoinTeacherDTO joinTeacherDTO);

    //교사 이메일 중복 검사
    boolean findDuplicateTeacherEmail(JoinTeacherDTO joinTeacherDTO);
}
