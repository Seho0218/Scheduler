package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Teacher.FindIdDTO;
import com.attendance.scheduler.Dto.Teacher.FindPasswordDTO;
import com.attendance.scheduler.Dto.Teacher.PwdEditDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.CertService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertServiceImpl implements CertService {

	private final JavaMailSender mailSender;

	private final TeacherRepository teacherRepository;

	private final PasswordEncoder passwordEncoder;


	/*
	* Confirm id exist
	* */
	@Override
	public String findIdByEmail(FindIdDTO findIdDTO) {

		TeacherEntity byTeacherIdIs = teacherRepository
				.findByEmailIs(findIdDTO.getEmail());

		if(byTeacherIdIs==null){
			return null;
		}
		return byTeacherIdIs.getTeacherId();
	}

	@Override
	public boolean idConfirmation(FindPasswordDTO findPasswordDTO) {
		TeacherEntity byTeacherIdIs = teacherRepository
				.findByTeacherIdIs(findPasswordDTO.getTeacherId());
		return byTeacherIdIs != null;
	}

	@Override
	public boolean emailConfirmation(FindPasswordDTO findPasswordDTO) {
		TeacherEntity byTeacherIdIs
				= teacherRepository.findByEmailIs(findPasswordDTO.getEmail());
		return byTeacherIdIs != null;
	}

	@Override
	@Transactional
	public void sendUserId(FindIdDTO findIdDTO) {
		SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
		simpleMailMessage.setFrom("ghdtpgh8913@gmail.com");
		simpleMailMessage.setTo(findIdDTO.getEmail());
		simpleMailMessage.setSubject("아이디 찾기");

		String sb = "가입하신 아이디는" +
				System.lineSeparator() +
				findIdDTO.getId() + "입니다";

		simpleMailMessage.setText(sb);
		new Thread(() -> mailSender.send(simpleMailMessage)).start();
	}

	@Override
	@Transactional
	public void setupAuthNum(FindPasswordDTO findPasswordDTO, HttpSession session) {

		String teacherId = findPasswordDTO.getTeacherId();
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
		simpleMailMessage.setFrom("ghdtpgh8913@gmail.com");
		simpleMailMessage.setTo(userEmail);
		simpleMailMessage.setSubject("비밀번호 찾기 인증번호");

		String text = "인증번호는 " + authNum + "입니다";

		simpleMailMessage.setText(text);
		new Thread(() -> mailSender.send(simpleMailMessage)).start();
	}

	@Override
	@Transactional
	public void PwdEdit(PwdEditDTO pwdEditDTO) {
		TeacherEntity byTeacherIdIs = teacherRepository
				.findByTeacherIdIs(pwdEditDTO.getTeacherId());

		log.info("pwdEditDTO.getTeacherId()={}",pwdEditDTO.getTeacherId());
		log.info("pwdEditDTO.getPassword()={}",pwdEditDTO.getPassword());

		String encodePassword = passwordEncoder.encode(pwdEditDTO.getPassword());
		byTeacherIdIs.updatePassword(encodePassword);
		teacherRepository.save(byTeacherIdIs);
	}
}
