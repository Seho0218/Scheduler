package com.attendance.scheduler.infra.email;

import com.attendance.scheduler.member.teacher.dto.FindIdDTO;
import jakarta.servlet.http.HttpSession;

public interface EmailService {

    void sendUserId(FindIdDTO findIdDTO);

    void sendAuthNum(FindPasswordDTO findPasswordDTO, HttpSession session);

    void sendEmail(EmailMessageDTO emailMessageDTO);
}
