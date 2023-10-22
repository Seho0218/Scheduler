package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Admin.EmailEditDTO;
import com.attendance.scheduler.Entity.AdminEntity;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.AdminRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.AdminService;
import com.attendance.scheduler.Service.JoinService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.attendance.scheduler.Config.TestDataSet.teacherDTO;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    public AdminService adminService;

    @Autowired
    public AdminRepository adminRepository;

    @Autowired
    private JoinService joinService;

    @Autowired
    private TeacherRepository teacherRepository;

    private ApproveTeacherDTO approveTeacherDTO;

    @BeforeEach
    void joinTestTeacherAccount(){
        joinService.joinTeacher(teacherDTO());

    }

    @Test
    @DisplayName("교사에게 권한 부여")
    void grantAuth() {

        approveTeacherDTO = new ApproveTeacherDTO();
        approveTeacherDTO.setUsername(teacherDTO().getUsername());

        //When
        adminService.approveAuth(approveTeacherDTO);

        //Then
        TeacherEntity teacherEntity = teacherRepository
                .findByUsernameIs(teacherDTO().getUsername());
        assertTrue(teacherEntity.isApproved());
    }

    @Test
    @DisplayName("교사에게서 권한 회수")
    void revokeAuth() {

        //Given
        approveTeacherDTO = new ApproveTeacherDTO();
        approveTeacherDTO.setUsername(teacherDTO().getUsername());

        //When
        adminService.revokeAuth(approveTeacherDTO);

        //Then
        TeacherEntity teacherEntity = teacherRepository
                .findByUsernameIs(teacherDTO().getUsername());
        assertFalse(teacherEntity.isApproved());
    }

    @Test
    @DisplayName("이메일 변경")
    void updateEmail(){

        //Given
        EmailEditDTO emailEditDTO = new EmailEditDTO();
        emailEditDTO.setUsername("admin");
        emailEditDTO.setAdminEmail("adminTest@gmail.com");

        //When
        System.out.println("emailEditDTO = " + emailEditDTO);
        adminService.updateEmail(emailEditDTO);
        AdminEntity byUsernameIs = adminRepository
                .findByUsernameIs(emailEditDTO.getUsername());

        //Then
        assertEquals(emailEditDTO.getAdminEmail(),byUsernameIs.getAdminEmail());
    }

    @AfterEach
    void test(){
        teacherRepository.deleteByUsernameIs(teacherDTO().getUsername());
    }

}