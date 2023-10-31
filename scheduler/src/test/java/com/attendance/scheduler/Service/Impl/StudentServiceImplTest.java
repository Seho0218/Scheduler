package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Config.Authority.UserDetailService;
import com.attendance.scheduler.Dto.LoginDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Entity.StudentEntity;
import com.attendance.scheduler.Repository.jpa.StudentRepository;
import com.attendance.scheduler.Service.TeacherService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.attendance.scheduler.Config.TestDataSet.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudentServiceImplTest {

    @Autowired private TeacherService teacherService;
    @Autowired private StudentRepository studentRepository;
    @Autowired private UserDetailService userDetailsService;

    @BeforeEach
    void beforeEach(){
        boolean duplicateTeacherID = teacherService
                .findDuplicateTeacherID(testTeacherDataSet());

        if (!duplicateTeacherID) {
            teacherService.joinTeacher(testTeacherDataSet());
        }

        //Given, 교사 로그인
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(testTeacherDataSet().getUsername());

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(testTeacherDataSet().getUsername(),
                        testTeacherDataSet().getPassword() , userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        StudentClassDTO studentClassDTO = new StudentClassDTO();
        studentClassDTO.setStudentName(testStudentClassDataSet().getStudentName());
    }

    @Test
    @DisplayName("학생 인적 사항 저장")
    void saveStudentInformation() {

        //Given
        studentRepository.save(testStudentInformationDTO().toEntity());

        //Then
        Optional<StudentEntity> studentEntityByStudentName = studentRepository
                .findStudentEntityByStudentNameIs(testStudentInformationDTO().getStudentName());

        assertThat(testStudentInformationDTO().getStudentName())
                .isEqualTo(studentEntityByStudentName.get().getStudentName());
    }

    @Test
    @DisplayName("학생 정보 삭제(이름이 아닌 고유번호로)")
    void deleteStudentEntityById() {

        //Given, 교사 로그인
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(testTeacherDataSet().getUsername());

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(testTeacherDataSet().getUsername(),
                        testTeacherDataSet().getPassword() , userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        //힉생정보
        studentRepository.save(testStudentInformationDTO().toEntity());
        studentRepository.save(test2StudentInformationDTO().toEntity());


        //When
        Optional<StudentEntity> studentEntityByStudentName = studentRepository
                .findStudentEntityByStudentNameIs(testStudentInformationDTO().getStudentName());

        studentRepository.deleteStudentEntityById(studentEntityByStudentName.get().getId());


        //Then
        Optional<StudentEntity> studentEntityById = studentRepository
                .findStudentEntityById(studentEntityByStudentName.get().getId());

        assertThat(studentEntityById).isEmpty();
    }
}
