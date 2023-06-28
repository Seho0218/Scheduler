package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.FindIdDTO;
import com.attendance.scheduler.Dto.Teacher.FindPasswordDTO;
import com.attendance.scheduler.Dto.Teacher.PwdEditDTO;
import jakarta.servlet.http.HttpSession;

public interface CertService {

    /*
    * find id By Email*/
    String findIdByEmail(FindIdDTO findIdDTO);
    /*
    * send UserId by Email*/
    void sendUserId(FindIdDTO findIdDTO);

    /*
    * id and Email Confirmation*/
    boolean idConfirmation(FindPasswordDTO findPasswordDTO);
    boolean emailConfirmation(FindPasswordDTO findPasswordDTO);

    /*
    * check AuthNum
    * */
    void setupAuthNum(FindPasswordDTO findPasswordDTO, HttpSession session);

    /*
    pwd Edit
    */
    void PwdEdit(PwdEditDTO pwdEditDTO);

}
 