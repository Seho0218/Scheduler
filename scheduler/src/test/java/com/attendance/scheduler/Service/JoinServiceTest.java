package com.attendance.scheduler.Service;

import com.attendance.scheduler.Config.Authority.UserDetailService;
import com.attendance.scheduler.Dto.LoginDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("교사 회원가입")
    void joinTeacher() {

        //Given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("teacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setName("김교사");

        //When
        joinService.joinTeacher(joinTeacherDTO);

        //Then
        TeacherEntity byTeacherIdIs = teacherRepository.findByUsernameIs("teacher");
        assertEquals("teacher", byTeacherIdIs.getUsername());

    }

    @Test
    @DisplayName("아이디 중복 확인")
    void findDuplicateTeacherId() {

        //given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("teacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setName("김교사");

        //when
        joinService.joinTeacher(joinTeacherDTO);
        String teacherId = joinService.findDuplicateTeacherId(joinTeacherDTO).getUsername();

        //then
        assertEquals("teacher", teacherId);
    }

    @Test
    @DisplayName("교사 로그인")
    void loginTeacher() {

        //Given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("teacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setName("김교사");
        joinTeacherDTO.setApproved(true);

        joinService.joinTeacher(joinTeacherDTO);


        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("teacher");
        loginDTO.setPassword("123");

        //when
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        //then
        boolean matches = passwordEncoder
                .matches(loginDTO.getPassword(), userDetails.getPassword());
        assertEquals("teacher", userDetails.getUsername());
        assertTrue(matches);
    }


}