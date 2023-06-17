package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Teacher.LoginTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
class BasicControllerTest {

    @Autowired
    private LoginService loginService;

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
        LoginTeacherDTO loginTeacherDTO = new LoginTeacherDTO();
        loginTeacherDTO.setTeacherId("testTeacher");
        loginTeacherDTO.setTeacherPassword("123");

        //When
        TeacherDTO teacher = loginService.loginTeacher(loginTeacherDTO);

        //Then
        assertEquals("testTeacher", teacher.getTeacherId());

    }
}
