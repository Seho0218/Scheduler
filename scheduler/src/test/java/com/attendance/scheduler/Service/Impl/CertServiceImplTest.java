package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.PwdEditDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.CertService;
import com.attendance.scheduler.Service.JoinService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CertServiceImplTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JoinService joinService;

    @Autowired
    private CertService certService;

    private JoinTeacherDTO joinTeacherDTO;

    @BeforeEach
    @DisplayName("회원가입")
    public void joinTeacherDTO() {
        Optional<TeacherEntity> existingTeacher
                = Optional.ofNullable(teacherRepository.findByUsernameIs("testTeacher"));

        if (existingTeacher.isEmpty()) {
            joinTeacherDTO = new JoinTeacherDTO();
            joinTeacherDTO.setUsername("testTeacher");
            joinTeacherDTO.setPassword("123");
            joinTeacherDTO.setEmail("testEmail@gmail.com");
            joinService.joinTeacher(joinTeacherDTO);
        }
    }

    @Test
    @DisplayName("아이디 찾을 때, 이메일 검증")
    void findId() {
        boolean duplicateTeacherEmail
                = joinService.findDuplicateTeacherEmail(joinTeacherDTO);
        assertTrue(duplicateTeacherEmail);
    }

    @Test
    @DisplayName("id 유무 확인")
    void idConfirmation(){
        boolean existedByUsername
                = teacherRepository.existsByUsername("testTeacher");
        assertTrue(existedByUsername);
    }

    @Test
    @DisplayName("email 유무 확인")
    void emailConfirmation(){
        boolean existedByUsername
                = teacherRepository.existsByEmail("testEmail@gmail.com");
        assertTrue(existedByUsername);
    }

//    @Test
    @DisplayName("비밀번호 변경 참 거짓 확인")
    void pwdEdit() {

        //비밀번호 변경
        PwdEditDTO pwdEditDTO = new PwdEditDTO();
        pwdEditDTO.setUsername("testTeacher");
        pwdEditDTO.setPassword("root123!@#");

        //when

        certService.PwdEdit(pwdEditDTO);
        TeacherEntity byUsernameIs
                = teacherRepository.findByUsernameIs(pwdEditDTO.getUsername());

        //then
        //비밀번호 검증
        boolean pwdMatch
                = passwordEncoder.matches("root123!@#", byUsernameIs.getPassword());
        assertTrue(pwdMatch);
    }

    @AfterEach
    void afterEach(){
        teacherRepository.deleteByUsernameIs("testTeacher");
    }
}