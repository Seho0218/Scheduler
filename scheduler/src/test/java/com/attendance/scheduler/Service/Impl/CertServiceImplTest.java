package com.attendance.scheduler.Service.Impl;


import com.attendance.scheduler.Config.Authority.UserDetailService;
import com.attendance.scheduler.Dto.LoginDTO;
import com.attendance.scheduler.Dto.Teacher.PwdEditDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.CertService;
import com.attendance.scheduler.Service.TeacherService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.attendance.scheduler.Config.TestDataSet.testTeacherDataSet;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class CertServiceImplTest {

    @Autowired private TeacherService teacherService;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private CertService certService;
    @Autowired private UserDetailService userDetailsService;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeEach
    @DisplayName("회원가입")
    public void joinTeacherDTO() {
        Optional<TeacherEntity> existingTeacher = Optional
                .ofNullable(teacherRepository
                        .findByUsernameIs(testTeacherDataSet().getUsername()));

        if (existingTeacher.isEmpty()) {
            teacherService.joinTeacher(testTeacherDataSet());
        }
    }

    @Test
    @DisplayName("아이디 찾을 때, 이메일 검증")
    void findIdByEmail() {
        boolean duplicateTeacherEmail = teacherService
                .findDuplicateTeacherEmail(testTeacherDataSet());
        assertTrue(duplicateTeacherEmail);
    }

    @Test
    @DisplayName("ID 유무 확인")
    void idConfirmation(){
        boolean existedByUsername = teacherRepository
                .existsByUsername(testTeacherDataSet().getUsername());
        assertTrue(existedByUsername);
    }

    @Test
    @DisplayName("Email 유무 확인")
    void emailConfirmation(){
        boolean existedByUsername = teacherRepository
                .existsByEmail(testTeacherDataSet().getEmail());
        assertTrue(existedByUsername);
    }

        @Test
    @DisplayName("비밀번호 변경 참 거짓 확인")
    void pwdEdit() {

        //비밀번호 변경
        PwdEditDTO pwdEditDTO = new PwdEditDTO();
        pwdEditDTO.setUsername(testTeacherDataSet().getUsername());
        pwdEditDTO.setPassword("root123!@#");

            //Given, 교사 로그인
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUsername(testTeacherDataSet().getUsername());

            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginDTO.getUsername());

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(testTeacherDataSet().getUsername(),
                            testTeacherDataSet().getPassword() , userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

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
}