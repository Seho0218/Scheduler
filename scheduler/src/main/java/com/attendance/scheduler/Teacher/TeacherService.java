package com.attendance.scheduler.Teacher;

import com.attendance.scheduler.Infra.Dto.EmailDTO;
import com.attendance.scheduler.Teacher.Dto.JoinTeacherDTO;
import com.attendance.scheduler.Teacher.Dto.TeacherDTO;

import java.util.Optional;

public interface TeacherService {

    //교사 회원 가입
    void joinTeacher(JoinTeacherDTO joinTeacherDTO);

    //교사 회원 삭제
    void deleteTeacher(TeacherDTO teacherDTO);

    //교사 아이디 중복 검사
    boolean findDuplicateTeacherID(JoinTeacherDTO joinTeacherDTO);

    //교사 이메일 중복 검사
    boolean findDuplicateTeacherEmail(JoinTeacherDTO joinTeacherDTO);

    Optional<EmailDTO> findTeacherEmailByUsername(EmailDTO emailDTO);

}
