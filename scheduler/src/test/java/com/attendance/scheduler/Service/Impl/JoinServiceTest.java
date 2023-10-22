package com.attendance.scheduler.Service.Impl;


import com.attendance.scheduler.Config.Authority.UserDetailService;
import com.attendance.scheduler.Dto.LoginDTO;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.JoinService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.attendance.scheduler.Config.TestDataSet.teacherDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JoinServiceTest {

    @Autowired
    private JoinService joinService;

    @Autowired
    private UserDetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;


    @BeforeEach
    void joinTestTeacherAccount(){
        joinService.joinTeacher(teacherDTO());
    }

    @Test
    @DisplayName("교사 회원가입 확인")
    void joinTeacher() {
        boolean duplicateTeacherId = joinService
                .findDuplicateTeacherId(teacherDTO());
        assertTrue(duplicateTeacherId);
    }

    @Test
    @DisplayName("아이디 중복 확인")
    void findDuplicateTeacherId() {
        boolean findDuplicateTeacherId = joinService
                .findDuplicateTeacherId(teacherDTO());
        assertTrue(findDuplicateTeacherId);
    }

    @Test
    @DisplayName("이메일 중복 확인")
    void findDuplicateTeacherEmail(){
        boolean duplicateTeacherEmail = joinService
                .findDuplicateTeacherEmail(teacherDTO());
        assertTrue(duplicateTeacherEmail);
    }

    @Test
    @DisplayName("교사 로그인")
    void loginTeacher() {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(teacherDTO().getUsername());
        loginDTO.setPassword(teacherDTO().getPassword());

        //when
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        //then
        boolean matches = passwordEncoder
                .matches(loginDTO.getPassword(), userDetails.getPassword());
        assertEquals(teacherDTO().getUsername(), userDetails.getUsername());
        assertTrue(matches);
    }

    @AfterEach
    void afterEach(){
        teacherRepository.deleteByUsernameIs(teacherDTO().getUsername());
    }

}