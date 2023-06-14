package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.DeleteClassDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

public interface AdminService {

    // 수업 삭제
    void deleteClass(DeleteClassDTO deleteClassDTO);

    //교사 회원 가입
    void joinTeacher(JoinTeacherDTO joinTeacherDTO);

    //TODO 교사 정보
    TeacherDTO findDuplicateTeacherId(JoinTeacherDTO joinTeacherDTO);


    //TODO 관리자 회원 가입
}
