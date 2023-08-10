package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.AdminDTO;
import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.AdminRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.AdminService;
import com.attendance.scheduler.Service.JoinService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    public AdminService adminService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private JoinService joinService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("교사에게 권한 부여")
    void grantAuth() {

        //Given
        JoinTeacherDTO joinTeacherDTO = new JoinTeacherDTO();
        joinTeacherDTO.setTeacherId("testId");
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
        TeacherEntity byTeacherIdIs = teacherRepository.findByTeacherIdIs("testTeacher");
        boolean approved = byTeacherIdIs.isApproved();

        assertTrue(approved);
    }

    @Test
    void adminJoin() {
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setUsername("admin");
        adminDTO.setPassword(passwordEncoder.encode("123"));

        adminRepository.save(adminDTO.toEntity());

        assertEquals(adminRepository.findByAdminIdIs("admin").getAdminId(), "admin");
    }
}