package com.attendance.scheduler.admin.application;

import com.attendance.scheduler.admin.dto.ChangeTeacherDTO;
import com.attendance.scheduler.admin.dto.EditEmailDTO;
import com.attendance.scheduler.admin.dto.EmailDTO;
import com.attendance.scheduler.infra.email.FindPasswordDTO;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;
import com.attendance.scheduler.teacher.dto.TeacherDTO;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<TeacherDTO> getTeacherList();

    List<TeacherDTO> findTeacherInformation(String username);

    Optional<EmailDTO> findAdminEmailByID(EmailDTO emailDTO);
    void grantAuth(String teacherId);
    void revokeAuth(String teacherId);

    void changeExistTeacher(ChangeTeacherDTO changeTeacherDTO);
    void deleteTeacherAccount(String teacherId);

    boolean emailConfirmation(FindPasswordDTO findPasswordDTO);

    void initializePassword(PwdEditDTO pwdEditDTO);
    void updateEmail(EditEmailDTO editEmailDTO);
}
