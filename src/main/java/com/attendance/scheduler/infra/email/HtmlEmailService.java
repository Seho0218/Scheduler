package com.attendance.scheduler.infra.email;

import com.attendance.scheduler.member.teacher.dto.FindIdDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class HtmlEmailService implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendUserId(FindIdDTO findIdDTO) {
        sendEmail(EmailMessageDTO.builder()
                .from("dev.hsayho@gmail.com")
                .to(findIdDTO.getEmail())
                .subject("아이디 찾기")
                .message("가입하신 아이디는" +
                        System.lineSeparator() +
                        findIdDTO.getUsername() + "입니다"
                )
                .build());
    }

    @Override
    public void sendAuthNum(FindPasswordDTO findPasswordDTO, HttpSession session) {

        String id = findPasswordDTO.getUsername();
        String email = findPasswordDTO.getEmail();

        StringBuilder authNum = new StringBuilder();
        for(int i=0;i<6;i++) {
            authNum.append((int) (Math.random() * 10));
        }

        log.info("인증번호={}", authNum);
        log.info("이메일={} ", email);

        Map<String, Object> authNumMap = new HashMap<>();
        LocalDateTime expiryDateTime= LocalDateTime.now().plusMinutes(5);
        authNumMap.put(id, authNum.toString());
        authNumMap.put("endTime", expiryDateTime);

        session.setMaxInactiveInterval(300);
        session.setAttribute(id, authNumMap);

        sendEmail(EmailMessageDTO.builder()
                .from("dev.hsayho@gmail.com")
                .to(email)
                .subject("비밀번호 찾기")
                .message("인증번호는 " + authNum + "입니다")
                .build());
    }

    public void sendEmail(EmailMessageDTO emailMessageDTO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setFrom(emailMessageDTO.getFrom());
            mimeMessageHelper.setTo(emailMessageDTO.getTo());
            mimeMessageHelper.setSubject(emailMessageDTO.getSubject());
            mimeMessageHelper.setText(emailMessageDTO.getMessage(), true);
            javaMailSender.send(mimeMessage);
            log.info("sent email: {}", emailMessageDTO.getMessage());
        } catch (MessagingException e) {
            log.error("failed to send email", e);
            throw new RuntimeException(e);
        }
    }
}
