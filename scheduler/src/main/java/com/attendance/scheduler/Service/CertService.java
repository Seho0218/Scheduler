package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.FindIdDTO;
import com.attendance.scheduler.Dto.Teacher.FindPasswordDTO;
import com.attendance.scheduler.Dto.Teacher.PwdEditDTO;
import jakarta.servlet.http.HttpSession;

public interface CertService {

    String findIdByEmail(FindIdDTO findIdDTO);
    void sendUserId(FindIdDTO findIdDTO);

    boolean idConfirmation(FindPasswordDTO findPasswordDTO);
    boolean emailConfirmation(FindPasswordDTO findPasswordDTO);

    void setupAuthNum(FindPasswordDTO findPasswordDTO, HttpSession session);
    void PwdEdit(PwdEditDTO pwdEditDTO);

}
 