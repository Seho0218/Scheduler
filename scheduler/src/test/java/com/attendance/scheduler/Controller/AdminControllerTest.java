package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.AdminService;
import com.attendance.scheduler.Service.JoinService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    public AdminService adminService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private JoinService joinService;

    @Test
    @Transactional
    @DisplayName("교사에게 권한 부여")
    void grantAuth() {

        //Given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setUsername("testTeacher");
        joinTeacherDTO.setPassword("123");
        joinTeacherDTO.setEmail("ghdtpgh8913@gmail.com");
        joinTeacherDTO.setEmail("김교사");

        ApproveTeacherDTO approveTeacherDTO = new ApproveTeacherDTO();
        approveTeacherDTO.setUsername("testTeacher");
        approveTeacherDTO.setApproved(true);

        joinService.joinTeacher(joinTeacherDTO);

        //When
        adminService.approveAuth(approveTeacherDTO);

        //Then
        TeacherEntity teacherEntity = teacherRepository.findByUsernameIs("testTeacher");
        assertTrue(teacherEntity.isApproved());
    }

    @AfterEach
    void test(){
        teacherRepository.deleteByUsernameIs("testTeacher");
    }
}