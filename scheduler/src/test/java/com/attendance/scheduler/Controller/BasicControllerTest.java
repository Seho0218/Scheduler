package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.LoginDTO;
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
@RequiredArgsConstructor
class BasicControllerTest {

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Test
//    void submitForm() {
//        //given
//        ClassDTO classDTO = new ClassDTO();
//        classDTO.setStudentName("김김김");
//        classDTO.setMonday(1);
//        classDTO.setTuesday(2);
//        classDTO.setWednesday(3);
//        classDTO.setThursday(4);
//        classDTO.setFriday(5);
//
//        //when
//        submitService.saveClassTable(classDTO);
//
//        //then
//
//
//    }

    @Test
    @DisplayName("교사 로그인")
    void teacherLogin() {

        //Given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId("testTeacher");
        loginDTO.setPassword("123");

        //When
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getId(), loginDTO.getPassword()));

        //Then
        assertEquals("testTeacher", authentication.getName());

    }
}
