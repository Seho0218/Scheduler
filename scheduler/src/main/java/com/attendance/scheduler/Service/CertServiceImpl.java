package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.FindIdDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CertServiceImpl implements CertService {

	private final JavaMailSender mailSender;

	private final PasswordEncoder passwordEncoder;

	private final TeacherRepository teacherRepository;


	@Override
	public String findIdByEmail(FindIdDTO findIdDTO) {

		TeacherEntity byTeacherIdIs
				= teacherRepository.findByTeacherEmail(findIdDTO.getEmail());

		if(byTeacherIdIs==null){
			return null;
		}
		return byTeacherIdIs.getTeacherId();
	}

	@Override
	public String sendUserId(FindIdDTO findIdDTO) {
		SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
		simpleMailMessage.setTo(findIdDTO.getEmail());
		simpleMailMessage.setSubject("아이디 찾기");

		try {
			String sb = "가입하신 아이디는" +
					System.lineSeparator() +
					findIdDTO.getId() + "입니다";

			simpleMailMessage.setText(sb);
			new Thread(() -> mailSender.send(simpleMailMessage)).start();

			return "이메일로 아이디가 발송되었습니다. 이메일을 확인해주세요";
		}catch(Exception e){
			return "오류가 발생했습니다. 관리자에게 문의해 주세요";
		}
	}

//	@Override
//	public void PwdEdit(AdminCertDTO adminCertDTO) {
//
//		AdminDTO adminDTO = AdminDTO.getInstance();
//		String enPw=passwordEncoder.encode(adminCertDTO.getChangedPwd());
//
//		adminDTO.setAdminId(adminDTO.getAdminId());
//		adminDTO.setAdminPassword(enPw);
//
//		adminRepository.save(adminDTO.toEntity());
//    }

//////////////////////////////////////////////////////////////////////

//	//아이디 중복 체크
//	@Override
//	public int overlapCheck(AdminCertDTO adminCertDTO) {
//		return adminRepository.countByAdminIdIs(adminCertDTO.getAdminId());
//	}
//
//	@Override
//	public boolean emailCheck(AdminCertDTO adminCertDTO) {
//		String adminId = adminCertDTO.getAdminId();
//		String adminEmail = adminCertDTO.getAdminEmail();
//		String result = adminRepository.checkUserExists(adminId, adminEmail);
//		return "1".equals(result);
//	}

	@Override
	public void sendAuthNum(String userEmail, String authNum) {
		SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
		simpleMailMessage.setTo(userEmail);
		simpleMailMessage.setSubject("비밀번호 찾기 인증번호");

		String text = "인증번호는 " + authNum + "입니다";

		simpleMailMessage.setText(text);
		new Thread(() -> mailSender.send(simpleMailMessage)).start();
	}

}
