package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Teacher.FindIdDTO;
import com.attendance.scheduler.Dto.Teacher.FindPasswordDTO;
import com.attendance.scheduler.Dto.Teacher.PwdEditDTO;
import com.attendance.scheduler.Entity.AdminEntity;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.AdminRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.CertService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CertServiceImpl implements CertService {

	private final JavaMailSender mailSender;
	private final TeacherRepository teacherRepository;
	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	/*
	* Confirm id exist
	* */
	@Override
	public Optional<FindIdDTO> findIdByEmail(FindIdDTO findIdDTO) {
		Optional<TeacherEntity> optionalTeacherEntity
				= teacherRepository.findByEmailIs(findIdDTO.getEmail());

        return optionalTeacherEntity.map(teacherEntity -> {
			FindIdDTO resultDTO = new FindIdDTO();
			resultDTO.setId(teacherEntity.getUsername());
			resultDTO.setEmail(teacherEntity.getEmail());
			return resultDTO;
		});
    }

	@Override
	public boolean idConfirmation(FindPasswordDTO findPasswordDTO) {
		Optional<TeacherEntity> optionalTeacherEntity
				= teacherRepository.findByUsernameIs(findPasswordDTO.getUsername());
		return optionalTeacherEntity.isPresent();
	}

	@Override
	public boolean emailConfirmation(FindPasswordDTO findPasswordDTO) {
		Optional<TeacherEntity> optionalTeacherEntity
				= teacherRepository.findByEmailIs(findPasswordDTO.getEmail());
		return optionalTeacherEntity.isPresent();
	}

	@Override
	@Transactional
	public void sendUserId(FindIdDTO findIdDTO) {
		SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
		simpleMailMessage.setFrom("dev.hsayho@gmail.com");
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
	public void PwdEdit(PwdEditDTO pwdEditDTO) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String encodePassword = passwordEncoder.encode(pwdEditDTO.getPassword());

		if(auth.getAuthorities().toString().equals("[ROLE_TEACHER]")
				|| auth.getAuthorities().toString().equals("[ROLE_ANONYMOUS]")){
			final Optional<TeacherEntity> byTeacherIdIs = teacherRepository
					.findByUsernameIs(pwdEditDTO.getUsername());
			byTeacherIdIs.ifPresent(teacherEntity -> {
				teacherEntity.updatePassword(encodePassword);
				teacherRepository.save(teacherEntity);
			});
		}

		if(auth.getAuthorities().toString().equals("[ROLE_ADMIN]")){
			final Optional<AdminEntity> byAdminIdIs = adminRepository
					.findByUsernameIs(pwdEditDTO.getUsername());
			byAdminIdIs.ifPresent(adminEntity -> {
				adminEntity.updatePassword(encodePassword);
				adminRepository.save(adminEntity);
			});
		}
	}
}
