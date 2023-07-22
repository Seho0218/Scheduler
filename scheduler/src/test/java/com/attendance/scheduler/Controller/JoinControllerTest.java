package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.LoginDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Service.JoinService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
class JoinControllerTest {

    private static final String duplicateErrorMessage = "중복된 아이디 입니다.";

    @Autowired
    private JoinService joinService;



    @Test
    @Transactional
    @DisplayName("교사 회원가입")
    void approved() {

        //Given
        /*
         * 회원가입
         */
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setTeacherId("teacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setEmail("김교사");
        joinTeacherDTO.setApproved(true);

        /*
            로그인
         */
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("teacher");
        loginDTO.setPassword("123");


        //When
        joinService.joinTeacher(joinTeacherDTO);
        JoinTeacherDTO duplicateTeacherId = joinService.findDuplicateTeacherId(joinTeacherDTO);


        //Then
        if (duplicateTeacherId != null) {
            assertEquals("중복된 아이디 입니다.", duplicateErrorMessage);
        }
    }
}