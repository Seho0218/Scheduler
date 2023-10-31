package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.EditEmailDTO;
import com.attendance.scheduler.Dto.EmailDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<TeacherDTO> getTeacherList();

    Optional<EmailDTO> findAdminEmailByID(EmailDTO emailDTO);
    void grantAuth(ApproveTeacherDTO approveTeacherDTO);
    void revokeAuth(ApproveTeacherDTO approveTeacherDTO);
    void updateEmail(EditEmailDTO editEmailDTO);
}
