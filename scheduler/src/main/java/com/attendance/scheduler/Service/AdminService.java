package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.EditEmailDTO;
import com.attendance.scheduler.Dto.EmailDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

import java.util.List;

public interface AdminService {

    List<TeacherDTO> getTeacherList();

    List<EmailDTO> findAdminEmailByID(EmailDTO emailDTO);
    void grantAuth(ApproveTeacherDTO approveTeacherDTO);
    void revokeAuth(ApproveTeacherDTO approveTeacherDTO);
    void updateEmail(EditEmailDTO editEmailDTO);
}
