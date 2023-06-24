package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.FindIdDTO;
import com.attendance.scheduler.Dto.Teacher.FindPasswordDTO;

public interface CertService {

    String findIdByEmail(FindIdDTO findIdDTO);
    String sendUserId(FindIdDTO findIdDTO);

    boolean idConfirmation(FindPasswordDTO findPasswordDTO);
    boolean emailConfirmation(FindPasswordDTO findPasswordDTO);

//    void PwdEdit(AdminCertDTO adminCertDTO);
//
//    int overlapCheck(AdminCertDTO adminCertDTO);
//    boolean emailCheck(AdminCertDTO adminCertDTO);
    void sendAuthNum(String userEmail, String authNum);

}
 