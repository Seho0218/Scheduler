package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

import java.util.List;

public interface AdminService {

    List<TeacherDTO> getTeacherList();

    void approveAuth(ApproveTeacherDTO approveTeacherDTO);

    void revokeAuth(ApproveTeacherDTO approveTeacherDTO);

}
