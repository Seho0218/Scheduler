package com.attendance.scheduler.teacher.application;

import com.attendance.scheduler.infra.email.FindPasswordDTO;
import com.attendance.scheduler.teacher.dto.EditEmailDTO;
import com.attendance.scheduler.teacher.dto.FindIdDTO;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;

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
