package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Admin.AdminCertDTO;

public interface CertService {

    String findIdByEmail(AdminCertDTO adminCertDTO);
    void sendUserId(AdminCertDTO adminCertDTO);

    void PwdEdit(AdminCertDTO adminCertDTO);

    int overlapCheck(AdminCertDTO adminCertDTO);
    boolean emailCheck(AdminCertDTO adminCertDTO);
    void sendAuthNum(String userEmail, String authNum);

}
 