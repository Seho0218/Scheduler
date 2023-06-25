package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.FindIdDTO;
import com.attendance.scheduler.Dto.Teacher.FindPasswordDTO;

public interface CertService {

    String findIdByEmail(FindIdDTO findIdDTO);
    String sendUserId(FindIdDTO findIdDTO);

    boolean idConfirmation(FindPasswordDTO findPasswordDTO);
    boolean emailConfirmation(FindPasswordDTO findPasswordDTO);

    void sendAuthNum(String userEmail, String authNum);
    //    void PwdEdit(AdminCertDTO adminCertDTO);

}
 