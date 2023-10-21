package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Config.Authority.UserDetailService;
import com.attendance.scheduler.Dto.LoginDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.JoinService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Transactional
@RequiredArgsConstructor
class JoinServiceTest {

    @Autowired
    private JoinService joinService;

    @Autowired
    private UserDetailService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepository teacherRepository;

    private JoinTeacherDTO joinTeacherDTO;

    @BeforeEach
    void joinTestTeacherAccount(){
        //Given
        joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("testTeacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("testEmail@gmail.com");
        joinTeacherDTO.setName("김교사");
        joinTeacherDTO.setApproved(true);

        joinService.joinTeacher(joinTeacherDTO);
    }

    @Test
    @DisplayName("교사 회원가입 확인")
    void joinTeacher() {
        boolean duplicateTeacherId
                = joinService.findDuplicateTeacherId(joinTeacherDTO);
        assertTrue(duplicateTeacherId);
    }

    @Test
    @DisplayName("아이디 중복 확인")
    void findDuplicateTeacherId() {
        boolean findDuplicateTeacherId
                = joinService.findDuplicateTeacherId(joinTeacherDTO);
        assertTrue(findDuplicateTeacherId);
    }

    @Test
    @DisplayName("이메일 중복 확인")
    void findDuplicateTeacherEmail(){
        boolean duplicateTeacherEmail
                = joinService.findDuplicateTeacherEmail(joinTeacherDTO);
        assertTrue(duplicateTeacherEmail);
    }

    @Test
    @DisplayName("교사 로그인")
    void loginTeacher() {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testTeacher");
        loginDTO.setPassword("123");

        //when
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        //then
        boolean matches = passwordEncoder
                .matches(loginDTO.getPassword(), userDetails.getPassword());
        assertEquals("testTeacher", userDetails.getUsername());
        assertTrue(matches);
    }

    @Test
    @DisplayName("저장소 스트림으로 확인")
    void testStream(){
        joinTeacherDTO = new JoinTeacherDTO();

        Optional<TeacherEntity> optionalTeacherEntity
                = teacherRepository.findByEmailIs(joinTeacherDTO.getEmail());

        optionalTeacherEntity.ifPresent(teacherEntity
                -> assertEquals("ghdtpgh8913@gmail.com", teacherEntity.getEmail()));
    }
}