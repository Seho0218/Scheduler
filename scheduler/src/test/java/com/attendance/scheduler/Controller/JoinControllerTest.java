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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor
class JoinControllerTest {

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
        joinTeacherDTO.setUsername("testTeacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setName("김교사");
        joinTeacherDTO.setApproved(true);


        //When
        joinService.joinTeacher(joinTeacherDTO);

        //Then
        TeacherEntity teacherEntity = teacherRepository.findByUsernameIs("testTeacher");
        boolean existedByUsername = teacherRepository.existsByUsername(teacherEntity.getUsername());
        assertTrue(existedByUsername);
    }

    @AfterEach
    void afterEach(){
        teacherRepository.deleteByUsernameIs("testTeacher");
    }
}