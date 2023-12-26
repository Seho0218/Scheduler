package com.attendance.scheduler.infra.email;

import com.attendance.scheduler.member.teacher.dto.FindIdDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsoleEmailService implements EmailService {

    public void sendEmail(EmailMessageDTO emailMessageDTO) {
        log.info("sent email: {}", emailMessageDTO.getMessage());
    }

    @Override
    public void sendUserId(FindIdDTO findIdDTO) {
        log.info("sent email info: {}", findIdDTO);

    }

    @Override
    public void sendAuthNum(FindPasswordDTO findPasswordDTO, HttpSession session) {
        log.info("sent email info: {}", findPasswordDTO);
    }
}
