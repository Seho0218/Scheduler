package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.AdminCertDTO;
import com.attendance.scheduler.Dto.AdminDTO;
import com.attendance.scheduler.Entity.AdminEntity;
import com.attendance.scheduler.Repository.jpa.AdminRepository;
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

	private final AdminRepository adminRepository;


	@Override
	public String FindId(AdminCertDTO adminCertDTO) {
		AdminEntity adminEntityByAdminEmail =
				adminRepository.findAdminEntityByAdminEmail(adminCertDTO.getAdminEmail());
		return adminEntityByAdminEmail.getAdminId();
	}

	@Override
	public void PwdEdit(AdminCertDTO adminCertDTO) {

		AdminDTO adminDTO = AdminDTO.getInstance();
		String enPw=passwordEncoder.encode(adminCertDTO.getChangedPwd());

		adminDTO.setAdminId(adminDTO.getAdminId());
		adminDTO.setAdminPassword(enPw);

		adminRepository.save(adminDTO.toEntity());
    }

	@Override
	public void sendUserId(AdminCertDTO adminCertDTO) {
		SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
		simpleMailMessage.setTo(adminCertDTO.getAdminEmail());
		simpleMailMessage.setSubject("아이디 찾기");

		String sb = "가입하신 아이디는" +
				System.lineSeparator() +
				adminCertDTO.getAdminId() + "입니다";//genie_id.get(0)

		simpleMailMessage.setText(sb);

		new Thread(() -> mailSender.send(simpleMailMessage)).start();
	}

//////////////////////////////////////////////////////////////////////
//
//	@Override
//	public int overlapCheck(String value, String valueType) {
//
//		Map<String, String> map = new HashMap<>();
//		map.put("value", value);
//		map.put("valueType", valueType);
//
//		return cdao.overlapCheck(value, valueType);
//	}
//
//	@Override
//	public boolean emailCheck(AdminCertDTO adminCertDTO) {
//		Map<String, Object> map = new HashMap<>();
//		map.put("adminId", adminCertDTO.getAdminId());
//		map.put("adminEmail", adminCertDTO.getAdminPassword());
//		String result = cdao.emailCheck(map);
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
