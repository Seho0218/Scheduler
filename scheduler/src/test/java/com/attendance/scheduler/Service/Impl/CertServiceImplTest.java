package com.attendance.scheduler.Service.Impl;


import com.attendance.scheduler.Dto.Teacher.PwdEditDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.CertService;
import com.attendance.scheduler.Service.TeacherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.attendance.scheduler.Config.TestDataSet.sampleTeacherDataSet;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CertServiceImplTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CertService certService;

    @BeforeEach
    @DisplayName("회원가입")
    public void joinTeacherDTO() {
        Optional<TeacherEntity> existingTeacher = Optional
                .ofNullable(teacherRepository
                        .findByUsernameIs(sampleTeacherDataSet().getUsername()));

        if (existingTeacher.isEmpty()) {
            teacherService.joinTeacher(sampleTeacherDataSet());
        }
    }

    @Test
    @DisplayName("아이디 찾을 때, 이메일 검증")
    void findIdByEmail() {
        boolean duplicateTeacherEmail = teacherService
                .findDuplicateTeacherEmail(sampleTeacherDataSet());
        assertTrue(duplicateTeacherEmail);
    }

    @Test
    @DisplayName("ID 유무 확인")
    void idConfirmation(){
        boolean existedByUsername = teacherRepository
                .existsByUsername(sampleTeacherDataSet().getUsername());
        assertTrue(existedByUsername);
    }

    @Test
    @DisplayName("Email 유무 확인")
    void emailConfirmation(){
        boolean existedByUsername = teacherRepository
                .existsByEmail(sampleTeacherDataSet().getEmail());
        assertTrue(existedByUsername);
    }

    //    @Test
    @DisplayName("비밀번호 변경 참 거짓 확인")
    void pwdEdit() {

        //비밀번호 변경
        PwdEditDTO pwdEditDTO = new PwdEditDTO();
        pwdEditDTO.setUsername(sampleTeacherDataSet().getUsername());
        pwdEditDTO.setPassword("root123!@#");

        //when

        certService.PwdEdit(pwdEditDTO);
        TeacherEntity byUsernameIs = teacherRepository
                .findByUsernameIs(pwdEditDTO.getUsername());

        //then
        //비밀번호 검증
        boolean pwdMatch = passwordEncoder
                .matches("root123!@#", byUsernameIs.getPassword());
        assertTrue(pwdMatch);
    }

    @AfterEach
    void afterEach(){
        teacherRepository.deleteByUsernameIs(sampleTeacherDataSet().getUsername());
    }
}