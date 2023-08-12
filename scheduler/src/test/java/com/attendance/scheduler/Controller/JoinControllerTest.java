package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.JoinService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class JoinControllerTest {

    private static final String duplicateErrorMessage = "중복된 아이디 입니다.";

    @Autowired
    private JoinService joinService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @DisplayName("교사 회원가입")
    void approved() {

        //Given
        /*
         * 회원가입 정보 1
         */
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("testTeacher123");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setName("김교사");
        joinTeacherDTO.setApproved(true);
        joinService.joinTeacher(joinTeacherDTO);


        //When
        /*
         *   회원가입 정보 2
         */
        JoinTeacherDTO joinTeacherDTO2 = new JoinTeacherDTO();
        joinTeacherDTO2.setUsername("testTeacher123");
        joinTeacherDTO2.setPassword("123");
        joinTeacherDTO2.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO2.setName("김교사");
        joinTeacherDTO2.setApproved(true);

        //Then

        TeacherEntity testTeacher = teacherRepository.findByUsernameIs("testTeacher");

        if (joinTeacherDTO2.getUsername().equals(testTeacher.getUsername())) {
            assertEquals("중복된 아이디 입니다.", duplicateErrorMessage);
        }
    }

    @AfterEach
    void afterEach(){
        teacherRepository.deleteByUsernameIs("testTeacher123");
    }
}