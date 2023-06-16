package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.LoginTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@RequiredArgsConstructor
class TeacherServiceTest {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void joinTeacher() {

        //Given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setTeacherId("teacher");
        joinTeacherDTO.setTeacherPassword("123");
        joinTeacherDTO.setTeacherEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setTeacherName("김교사");

        //When
        teacherService.joinTeacher(joinTeacherDTO);

        //Then
        TeacherEntity byTeacherIdIs = teacherRepository.findByTeacherIdIs("teacher");
        assertEquals("teacher", byTeacherIdIs.getTeacherId());

    }

    @Test
    void findDuplicateTeacherId() {

        //given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setTeacherId("teacher");
        joinTeacherDTO.setTeacherPassword("123");
        joinTeacherDTO.setTeacherEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setTeacherName("김교사");

        //when
        teacherService.joinTeacher(joinTeacherDTO);
        String teacherId = teacherService.findDuplicateTeacherId(joinTeacherDTO).getTeacherId();

        //then
        assertEquals("teacher", teacherId);
    }

    @Test
    void loginTeacher() {
        //Given
        LoginTeacherDTO loginTeacherDTO = new LoginTeacherDTO();
        loginTeacherDTO.setTeacherId("testTeacher");
        loginTeacherDTO.setTeacherPassword("123");


        //when
        TeacherDTO loginTeacher = teacherService.loginTeacher(loginTeacherDTO);

        //then
        assertEquals("testTeacher", loginTeacher.getTeacherId());
    }
}