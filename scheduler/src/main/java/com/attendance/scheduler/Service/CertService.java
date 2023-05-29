package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.AdminCertDTO;

public interface CertService {

    String FindId(AdminCertDTO adminCertDTO);
    void sendUserId(AdminCertDTO adminCertDTO);

    void PwdEdit(AdminCertDTO adminCertDTO);

//    int overlapCheck(String value, String valueType);

//    boolean emailCheck(AdminCertDTO adminCertDTO);

    void sendAuthNum(String userEmail, String authNum);

}
 