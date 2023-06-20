package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.FindIdDTO;

public interface CertService {

    String findIdByEmail(FindIdDTO findIdDTO);
    String sendUserId(FindIdDTO findIdDTO);

//    void PwdEdit(AdminCertDTO adminCertDTO);
//
//    int overlapCheck(AdminCertDTO adminCertDTO);
//    boolean emailCheck(AdminCertDTO adminCertDTO);
    void sendAuthNum(String userEmail, String authNum);

}
 