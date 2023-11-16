package com.attendance.scheduler.admin.application;

import com.attendance.scheduler.admin.dto.*;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;
import com.attendance.scheduler.teacher.dto.TeacherDTO;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<TeacherDTO> getTeacherList();

    List<TeacherDTO> findTeacherInformation(String username);

    Optional<EmailDTO> findAdminEmailByID(EmailDTO emailDTO);
    void grantAuth(ApproveTeacherDTO approveTeacherDTO);
    void revokeAuth(ApproveTeacherDTO approveTeacherDTO);

    void changeExistTeacher(ChangeTeacherDTO changeTeacherDTO);
    void deleteTeacherAccount(ApproveTeacherDTO approveTeacherDTO);

    boolean emailConfirmation(FindPasswordDTO findPasswordDTO);

    /*
     * check AuthNum
     * */
    void setupAuthNum(FindPasswordDTO findPasswordDTO, HttpSession session);

    void initializePassword(PwdEditDTO pwdEditDTO);
    void updateEmail(EditEmailDTO editEmailDTO);
}
