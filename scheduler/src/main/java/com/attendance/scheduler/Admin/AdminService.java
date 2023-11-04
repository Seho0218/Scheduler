package com.attendance.scheduler.Admin;

import com.attendance.scheduler.Admin.Dto.ApproveTeacherDTO;
import com.attendance.scheduler.Admin.Dto.ChangeTeacherDTO;
import com.attendance.scheduler.Infra.Dto.EditEmailDTO;
import com.attendance.scheduler.Infra.Dto.EmailDTO;
import com.attendance.scheduler.Teacher.Dto.TeacherDTO;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<TeacherDTO> getTeacherList();

    Optional<EmailDTO> findAdminEmailByID(EmailDTO emailDTO);
    void grantAuth(ApproveTeacherDTO approveTeacherDTO);
    void revokeAuth(ApproveTeacherDTO approveTeacherDTO);

    void changeExistTeacher(ChangeTeacherDTO changeTeacherDTO);
    void deleteTeacherAccount(ApproveTeacherDTO approveTeacherDTO);
    void updateEmail(EditEmailDTO editEmailDTO);
}
