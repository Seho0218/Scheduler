package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Config.Authority.UserDetailService;
import com.attendance.scheduler.Dto.EmailDTO;
import com.attendance.scheduler.Dto.LoginDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.TeacherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.attendance.scheduler.Config.TestDataSet.sampleTeacherDataSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TeacherServiceImplTest {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserDetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;


    @BeforeEach
    void joinTestTeacherAccount(){
        Optional<TeacherEntity> existingTeacher = Optional
                .ofNullable(teacherRepository
                        .findByUsernameIs(sampleTeacherDataSet().getUsername()));

        if (existingTeacher.isEmpty()) {
            teacherService.joinTeacher(sampleTeacherDataSet());
        }
    }

    @Test
    @DisplayName("교사 회원가입 확인")
    void joinTeacher() {
        boolean duplicateTeacherId = teacherService
                .findDuplicateTeacherID(sampleTeacherDataSet());
        assertTrue(duplicateTeacherId);
    }

    @Test
    @DisplayName("아이디 중복 확인")
    void findDuplicateTeacherID() {
        boolean findDuplicateTeacherId = teacherService
                .findDuplicateTeacherID(sampleTeacherDataSet());
        assertTrue(findDuplicateTeacherId);
    }

    @Test
    @DisplayName("이메일 중복 확인")
    void findDuplicateTeacherEmail(){
        boolean duplicateTeacherEmail = teacherService
                .findDuplicateTeacherEmail(sampleTeacherDataSet());
        assertTrue(duplicateTeacherEmail);
    }

    @Test
    @DisplayName("아이디로 이메일 정보 찾기")
    void findTeacherEmailByID() {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setUsername(sampleTeacherDataSet().getUsername());

        TeacherEntity teacherEntity = teacherRepository
                .findByUsernameIs(emailDTO.getUsername());

        EmailDTO build = EmailDTO.builder()
                .username(teacherEntity.getUsername())
                .email(teacherEntity.getEmail())
                .build();

        assertEquals(sampleTeacherDataSet().getUsername(), build.getUsername());
        assertEquals(sampleTeacherDataSet().getEmail(), build.getEmail());

    }

    @Test
    @DisplayName("교사 로그인")
    void loginTeacher() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(sampleTeacherDataSet().getUsername());
        loginDTO.setPassword(sampleTeacherDataSet().getPassword());

        //when
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        //then
        boolean matches = passwordEncoder
                .matches(loginDTO.getPassword(), userDetails.getPassword());
        assertEquals(sampleTeacherDataSet().getUsername(), userDetails.getUsername());
        assertTrue(matches);
    }

    @AfterEach
    void afterEach(){
        teacherRepository.deleteByUsernameIs(sampleTeacherDataSet().getUsername());
    }

}