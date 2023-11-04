package com.attendance.scheduler.Infra;

import com.attendance.scheduler.Infra.Dto.FindIdDTO;
import com.attendance.scheduler.Infra.Dto.FindPasswordDTO;
import com.attendance.scheduler.Infra.Dto.PwdEditDTO;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public interface CertService {

    /*
    * find id By Email*/
    Optional<FindIdDTO> findIdByEmail(FindIdDTO findIdDTO);
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
 