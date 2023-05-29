package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.AdminCertDTO;
import com.attendance.scheduler.Entity.AdminEntity;
import com.attendance.scheduler.Repository.jpa.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CertServiceImplTest {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("아이디 찾을 때, 이메일 검증")
    void findId() {
        //given
        AdminCertDTO certFindIdDTO = AdminCertDTO.getInstance();
        certFindIdDTO.setAdminId("admin");
        certFindIdDTO.setAdminPassword("Root123!@#");
        certFindIdDTO.setAdminEmail("ghdtpgh8913@gmail.com");

        //when
        adminRepository.save(certFindIdDTO.toEntity());
        AdminEntity adminEntityByAdminEmail = adminRepository.findAdminEntityByAdminEmail(certFindIdDTO.getAdminEmail());

        //then
        assertEquals("ghdtpgh8913@gmail.com", adminEntityByAdminEmail.getAdminEmail());

    }

    @Test
    @DisplayName(" 비밀번호 변경 참 거짓 확인 ")
    void pwdEditOk() {

        //given
        // 임의 관리자 객체생성
        AdminCertDTO adminCertDTO = AdminCertDTO.getInstance();
        adminCertDTO.setAdminId("admin");

        //비밀번호 암호화
        String encodedPwd = passwordEncoder.encode("Root123!@#");
        adminCertDTO.setAdminPassword(encodedPwd);

        //when
        adminRepository.save(adminCertDTO.toEntity());

        //then
        adminRepository.findAdminEntityByAdminId(adminCertDTO.getAdminId());

        //비밀번호 검증
        boolean pwdMatch = passwordEncoder.matches("Root123!@#", adminCertDTO.getAdminPassword());

        assertTrue(pwdMatch);
    }
}