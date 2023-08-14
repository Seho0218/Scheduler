package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class CertServiceImplTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("아이디 찾을 때, 이메일 검증")
    void findId() {
        //given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("testTeacher123");
        joinTeacherDTO.setPassword("Root123!@#");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        teacherRepository.save(joinTeacherDTO.toEntity());

        //when
        Optional<TeacherEntity> optionalTeacherEntity = teacherRepository.findByEmailIs(joinTeacherDTO.getEmail());

        //then
        optionalTeacherEntity.ifPresent(teacherEntity -> {
            assertEquals("ghdtpgh8913@gmail.com", teacherEntity.getEmail());
        });
    }

    @Test
    @DisplayName(" 비밀번호 변경 참 거짓 확인 ")
    void pwdEdit() {

        //given
        // 임의 관리자 객체생성
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("testTeacher");

        //비밀번호 암호화
        String encodedPwd = passwordEncoder.encode("Root123!@#");
        joinTeacherDTO.setPassword(encodedPwd);
        teacherRepository.save(joinTeacherDTO.toEntity());

        //when
        teacherRepository.findByUsernameIs(joinTeacherDTO.getUsername());

        //then
        //비밀번호 검증
        boolean pwdMatch = passwordEncoder.matches("Root123!@#", joinTeacherDTO.getPassword());

        assertTrue(pwdMatch);
    }
}