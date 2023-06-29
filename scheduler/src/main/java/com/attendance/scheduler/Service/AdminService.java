package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

import java.util.List;

public interface AdminService {

    List<TeacherDTO> findAllTeacherAccount();

    void approveTeacher(ApproveTeacherDTO approveTeacherDTO);

}
