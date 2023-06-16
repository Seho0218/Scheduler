package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
class AdminControllerTest {

    @Autowired
    private TeacherService teacherService;

    @Test
    @DisplayName("교사 로그인")
    void teacherLogin() {

        //Given

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setTeacherId("testTeacher");
        teacherDTO.setTeacherPassword("123");

        //When
        TeacherDTO teacher = teacherService.loginTeacher(teacherDTO);

        //Then
        assertEquals("testTeacher", teacher.getTeacherId());

    }
}