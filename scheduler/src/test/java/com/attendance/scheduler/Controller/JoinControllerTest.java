package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Service.TeacherService;
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
    private TeacherService teacherService;

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
        joinTeacherDTO.setTeacherPassword("123");
        joinTeacherDTO.setTeacherEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setTeacherName("김교사");

        /*
            로그인 검증
         */
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setTeacherId("teacher");
        teacherDTO.setTeacherPassword("123");


        //When
        JoinTeacherDTO duplicateTeacherId = teacherService.findDuplicateTeacherId(joinTeacherDTO);
        teacherService.joinTeacher(joinTeacherDTO);


        //Then
        if (duplicateTeacherId != null) {
            assertEquals("중복된 아이디 입니다.", duplicateErrorMessage);
        }

        TeacherDTO teacher = teacherService.loginTeacher(teacherDTO);
        assertEquals("teacher", teacher.getTeacherId());

    }
}