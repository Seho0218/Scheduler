package com.attendance.scheduler.Service;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@RequiredArgsConstructor
class JoinServiceTest {

    @Autowired
    private JoinService joinService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @DisplayName("교사 회원가입")
    void joinTeacher() {

        //Given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setTeacherId("teacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setName("김교사");

        //When
        joinService.joinTeacher(joinTeacherDTO);

        //Then
        TeacherEntity byTeacherIdIs = teacherRepository.findByTeacherIdIs("teacher");
        assertEquals("teacher", byTeacherIdIs.getTeacherId());

    }

    @Test
    @DisplayName("아이디 중복 확인")
    void findDuplicateTeacherId() {

        //given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setTeacherId("teacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setName("김교사");

        //when
        joinService.joinTeacher(joinTeacherDTO);
        String teacherId = joinService.findDuplicateTeacherId(joinTeacherDTO).getTeacherId();

        //then
        assertEquals("teacher", teacherId);
    }

    @Test
    @DisplayName("교사 로그인")
    void loginTeacher() {

        //Given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setTeacherId("teacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setName("김교사");
        joinService.joinTeacher(joinTeacherDTO);


        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setTeacherId("teacher");
        loginDTO.setPassword("123");

        //when
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getTeacherId(), loginDTO.getPassword()));

        //then
        assertEquals("teacher", authentication.getName());
    }
}