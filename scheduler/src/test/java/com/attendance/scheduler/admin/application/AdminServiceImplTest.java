package com.attendance.scheduler.admin.application;

import com.attendance.scheduler.admin.domain.AdminEntity;
import com.attendance.scheduler.admin.dto.ApproveTeacherDTO;
import com.attendance.scheduler.admin.dto.ChangeTeacherDTO;
import com.attendance.scheduler.admin.dto.EditEmailDTO;
import com.attendance.scheduler.admin.dto.EmailDTO;
import com.attendance.scheduler.admin.repository.AdminRepository;
import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.repository.StudentJpaRepository;
import com.attendance.scheduler.student.repository.StudentRepository;
import com.attendance.scheduler.teacher.application.TeacherService;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.repository.TeacherJpaRepository;
import com.attendance.scheduler.teacher.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.attendance.scheduler.config.TestDataSet.testTeacherDataSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Transactional
class AdminServiceImplTest {

    @Autowired public AdminService adminService;
    @Autowired public AdminRepository adminRepository;
    @Autowired private TeacherService teacherService;
    @Autowired private TeacherJpaRepository teacherJpaRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private StudentJpaRepository studentJpaRepository;
    @Autowired private StudentRepository studentRepository;

    private ApproveTeacherDTO approveTeacherDTO;

//    @BeforeEach
    void joinSampleTeacherAccount(){
        Optional<TeacherEntity> existingTeacher = Optional
                .ofNullable(teacherJpaRepository
                        .findByUsernameIs(testTeacherDataSet().getUsername()));
        if (existingTeacher.isEmpty()) {
            teacherService.joinTeacher(testTeacherDataSet());
        }
    }


    @Test
    void findAdminEmailByID() {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setUsername("admin");
        AdminEntity adminAccount = adminRepository
                .findByUsernameIs(emailDTO.getUsername());

        EmailDTO build = EmailDTO.builder()
                .username(adminAccount.getUsername())
                .email(adminAccount.getEmail())
                .build();

        assertThat("admin").isEqualTo(build.getUsername());
        assertThat("adminTest@gmail.com").isEqualTo( build.getEmail());
    }

    @Test
    @DisplayName("교사에게 권한 부여")
    void grantAuth() {

        //Given
        approveTeacherDTO = new ApproveTeacherDTO();
        approveTeacherDTO.setUsername(testTeacherDataSet().getUsername());

        //When
        adminService.grantAuth(approveTeacherDTO);

        //Then
        TeacherEntity teacherEntity = teacherJpaRepository
                .findByUsernameIs(testTeacherDataSet().getUsername());
        assertTrue(teacherEntity.isApproved());
    }



    @Test
    @DisplayName("교사에게서 권한 회수")
    void revokeAuth() {

        //Given
        approveTeacherDTO = new ApproveTeacherDTO();
        approveTeacherDTO.setUsername(testTeacherDataSet().getUsername());

        //When
        adminService.revokeAuth(approveTeacherDTO);

        //Then
        TeacherEntity teacherEntity = teacherJpaRepository
                .findByUsernameIs(testTeacherDataSet().getUsername());
        assertFalse(teacherEntity.isApproved());
    }



    @Test
    void changeExistTeacher() {
        ChangeTeacherDTO changeTeacherDTO = new ChangeTeacherDTO();
        changeTeacherDTO.setTeacherId(2L);
        changeTeacherDTO.setStudentId(2L);

        System.out.println("1 = " + 1);
        Optional<TeacherEntity> teacherEntity = teacherRepository.getTeacherEntity(changeTeacherDTO.getTeacherId());
        System.out.println("2 = " + 2);
        Optional<StudentEntity> studentEntity = studentRepository.getStudentEntity(changeTeacherDTO.getStudentId());
        System.out.println("3 = " + 3);
        studentEntity.get().updateTeacherName(teacherEntity.get().getTeacherName());
        System.out.println("4 = " + 4);
        studentEntity.get().setTeacherEntity(teacherEntity.get());
        System.out.println("5 = " + 5);

        studentJpaRepository.save(studentEntity.get());
        System.out.println("6 = " + 6);
    }

    @Test
    @DisplayName("교사 계정 삭제")
    void deleteTeacher() {
        teacherJpaRepository.deleteByUsernameIs(testTeacherDataSet().getUsername());
        boolean duplicateTeacherId = teacherService
                .findDuplicateTeacherID(testTeacherDataSet());

        assertFalse(duplicateTeacherId);
    }

    @Test
    @DisplayName("이메일 변경")
    void updateEmail(){

        //Given
        EditEmailDTO editEmailDTO = new EditEmailDTO();
        editEmailDTO.setUsername("admin");
        editEmailDTO.setEmail("adminTest@gmail.com");

        //When
        adminService.updateEmail(editEmailDTO);
        AdminEntity byUsernameIs = adminRepository
                .findByUsernameIs(editEmailDTO.getUsername());

        //Then
        assertThat(editEmailDTO.getEmail()).isEqualTo(byUsernameIs.getEmail());
    }
}