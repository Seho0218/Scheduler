package com.attendance.scheduler.admin.application;

import com.attendance.scheduler.admin.dto.EditEmailDTO;
import com.attendance.scheduler.infra.email.FindPasswordDTO;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;

public interface AdminCertService {

    boolean emailConfirmation(FindPasswordDTO findPasswordDTO);

    void initializePassword(PwdEditDTO pwdEditDTO);
    void updateEmail(EditEmailDTO editEmailDTO);
}
