package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Config.Authority.UserDetailService;
import com.attendance.scheduler.Dto.EmailDTO;
import com.attendance.scheduler.Dto.LoginDTO;
import com.attendance.scheduler.Entity.StudentEntity;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.StudentRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.StudentService;
import com.attendance.scheduler.Service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.attendance.scheduler.Config.TestDataSet.testStudentInformationDTO;
import static com.attendance.scheduler.Config.TestDataSet.testTeacherDataSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeacherServiceImplTest {

    @Autowired private TeacherService teacherService;
    @Autowired private UserDetailService userDetailsService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private StudentService studentService;


    @BeforeEach
    void joinTestTeacherAccount(){
        boolean duplicateTeacherID = teacherService
                .findDuplicateTeacherID(testTeacherDataSet());

        if (!duplicateTeacherID) {
            teacherService.joinTeacher(testTeacherDataSet());
        }
    }

    @Test
    @DisplayName("교사 회원가입 확인")
    void joinTeacher() {
        boolean duplicateTeacherId = teacherService
                .findDuplicateTeacherID(testTeacherDataSet());
        assertTrue(duplicateTeacherId);
    }

    @Test
    @DisplayName("교사 계정 삭제")
    void deleteTeacher() {
        teacherRepository.deleteByUsernameIs(testTeacherDataSet().getUsername());

        boolean duplicateTeacherId = teacherService
                .findDuplicateTeacherID(testTeacherDataSet());

        assertFalse(duplicateTeacherId);
    }

    @Test
    @DisplayName("아이디 중복 확인")
    void findDuplicateTeacherID() {
        boolean findDuplicateTeacherId = teacherService
                .findDuplicateTeacherID(testTeacherDataSet());
        assertTrue(findDuplicateTeacherId);
    }

    @Test
    @DisplayName("이메일 중복 확인")
    void findDuplicateTeacherEmail(){
        boolean duplicateTeacherEmail = teacherService
                .findDuplicateTeacherEmail(testTeacherDataSet());
        assertTrue(duplicateTeacherEmail);
    }

    @Test
    @DisplayName("아이디로 이메일 정보 찾기")
    void findTeacherEmailByID() {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setUsername(testTeacherDataSet().getUsername());

        TeacherEntity teacherEntity = teacherRepository
                .findByUsernameIs(emailDTO.getUsername());

        EmailDTO build = EmailDTO.builder()
                .username(teacherEntity.getUsername())
                .email(teacherEntity.getEmail())
                .build();

        List<EmailDTO> emailDTOS = Collections.singletonList(build);

        assertEquals(testTeacherDataSet().getUsername(), emailDTOS.get(0).getUsername());
        assertEquals(testTeacherDataSet().getEmail(), emailDTOS.get(0).getEmail());

    }

    @Test
    @DisplayName("교사 로그인")
    void loginTeacher() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(testTeacherDataSet().getUsername());
        loginDTO.setPassword(testTeacherDataSet().getPassword());

        //when
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        //then
        boolean matches = passwordEncoder
                .matches(loginDTO.getPassword(), userDetails.getPassword());
        assertEquals(testTeacherDataSet().getUsername(), userDetails.getUsername());
        assertTrue(matches);
    }

    @Test
    @DisplayName("교사가 학생 정보를 등록")
    void registerStudentInformation() {

        //Given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(testTeacherDataSet().getUsername());

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(testTeacherDataSet().getUsername(),
                        null , userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);


        Optional<StudentEntity> studentEntityByStudentNameIs = studentRepository
                .findStudentEntityByStudentNameIs(testStudentInformationDTO().getStudentName());

        if(studentEntityByStudentNameIs.isEmpty()){
            studentService.registerStudentInformation(testStudentInformationDTO());
        }

        if (studentEntityByStudentNameIs.isPresent()) {
            String studentName = studentEntityByStudentNameIs.get().getStudentName();
            assertThat(testStudentInformationDTO().getStudentName()).isEqualTo(studentName);
        }
    }
}