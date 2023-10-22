package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.AdminDTO;
import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Admin.EmailEditDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

import java.util.List;

public interface AdminService {

    List<TeacherDTO> getTeacherList();

    AdminDTO findAdminAccountById(AdminDTO adminDTO);
    void approveAuth(ApproveTeacherDTO approveTeacherDTO);
    void revokeAuth(ApproveTeacherDTO approveTeacherDTO);
    void updateEmail(EmailEditDTO emailEditDTO);
}
