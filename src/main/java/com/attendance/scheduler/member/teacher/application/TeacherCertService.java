package com.attendance.scheduler.member.teacher.application;

import com.attendance.scheduler.infra.email.FindPasswordDTO;
import com.attendance.scheduler.member.teacher.dto.EditEmailDTO;
import com.attendance.scheduler.member.teacher.dto.FindIdDTO;
import com.attendance.scheduler.member.teacher.dto.PwdEditDTO;

import java.util.Optional;

public interface TeacherCertService {

    /*
     * find id By Email*/
    boolean idConfirmation(FindPasswordDTO findPasswordDTO);
    boolean emailConfirmation(FindPasswordDTO findPasswordDTO);

    Optional<FindIdDTO> findIdByEmail(FindIdDTO findIdDTO);

    /*
     * send UserId by Email*/
    void initializePassword(PwdEditDTO pwdEditDTO);
    void updateEmail(EditEmailDTO editEmailDTO);
}
