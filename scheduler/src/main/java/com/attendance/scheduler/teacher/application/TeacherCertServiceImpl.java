package com.attendance.scheduler.teacher.application;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.dto.EditEmailDTO;
import com.attendance.scheduler.teacher.dto.FindIdDTO;
import com.attendance.scheduler.teacher.dto.FindPasswordDTO;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;
import com.attendance.scheduler.teacher.repository.TeacherJpaRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeacherCertServiceImpl implements TeacherCertService {

    private final TeacherJpaRepository teacherJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Override
    public boolean idConfirmation(FindPasswordDTO findPasswordDTO) {
        return teacherJpaRepository
                .existsByUsername(findPasswordDTO.getUsername());
    }

    @Override
    public boolean emailConfirmation(FindPasswordDTO findPasswordDTO) {
        return teacherJpaRepository
                .existsByEmail(findPasswordDTO.getEmail());
    }

    @Override
    public Optional<FindIdDTO> findIdByEmail(FindIdDTO findIdDTO) {
        Optional<TeacherEntity> optionalTeacherEntity
                = Optional.ofNullable(teacherJpaRepository.findByUsernameIs(findIdDTO.getUsername()));

        return optionalTeacherEntity.map(teacherEntity -> {
            FindIdDTO resultDTO = new FindIdDTO();
            resultDTO.setUsername(teacherEntity.getUsername());
            resultDTO.setEmail(teacherEntity.getEmail());
            return resultDTO;
        });
    }

    @Override
    public void sendUserId(FindIdDTO findIdDTO) {
        SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
        simpleMailMessage.setFrom("dev.hsayho@gmail.com");
        simpleMailMessage.setTo(findIdDTO.getEmail());
        simpleMailMessage.setSubject("아이디 찾기");

        String sb = "가입하신 아이디는" +
                System.lineSeparator() +
                findIdDTO.getUsername() + "입니다";

        simpleMailMessage.setText(sb);
        new Thread(() -> mailSender.send(simpleMailMessage)).start();
    }

    @Override
    @Transactional
    public void setupAuthNum(FindPasswordDTO findPasswordDTO, HttpSession session) {

        String teacherId = findPasswordDTO.getUsername();
        String email = findPasswordDTO.getEmail();

        StringBuilder authNum = new StringBuilder();

        for(int i=0;i<6;i++) {
            authNum.append((int) (Math.random() * 10));
        }

        log.info("인증번호={}", authNum);
        log.info("이메일={} ", email);

        sendAuthNum(email, authNum.toString());

        Map<String, Object> authNumMap = new HashMap<>();

        LocalDateTime expiryDateTime= LocalDateTime.now().plusMinutes(5);

        authNumMap.put(teacherId, authNum.toString());
        authNumMap.put("endTime", expiryDateTime);

        session.setMaxInactiveInterval(300);
        session.setAttribute(teacherId, authNumMap);
    }

    public void sendAuthNum(String userEmail, String authNum) {
        SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
        simpleMailMessage.setFrom("dev.hsayho@gmail.com");
        simpleMailMessage.setTo(userEmail);
        simpleMailMessage.setSubject("비밀번호 찾기 인증번호");

        String text = "인증번호는 " + authNum + "입니다";

        simpleMailMessage.setText(text);
        new Thread(() -> mailSender.send(simpleMailMessage)).start();
    }

    @Override
    @Transactional
    public void initializePassword(PwdEditDTO pwdEditDTO) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String encodePassword = passwordEncoder.encode(pwdEditDTO.getPassword());

        pwdEditDTO.setPassword(encodePassword);

        final TeacherEntity byTeacherIdIs = teacherJpaRepository
                .findByUsernameIs(auth.getName());
        byTeacherIdIs.updatePassword(pwdEditDTO);
        teacherJpaRepository.save(byTeacherIdIs);
    }

    @Override
    public void updateEmail(EditEmailDTO editEmailDTO) {
        TeacherEntity teacherEntity = teacherJpaRepository.findByUsernameIs(editEmailDTO.getUsername());
        teacherEntity.updateEmail(editEmailDTO);
        teacherJpaRepository.save(teacherEntity);
    }
}
