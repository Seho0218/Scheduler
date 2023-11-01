package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Config.Authority.UserDetailService;
import com.attendance.scheduler.Dto.LoginDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Entity.StudentEntity;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.StudentRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.StudentService;
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
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@Transactional
class StudentServiceImplTest {

    @Autowired private TeacherService teacherService;
    @Autowired private StudentService studentService;
    @Autowired private StudentRepository studentRepository;
    @Autowired private UserDetailService userDetailsService;
    @Autowired
    private TeacherRepository teacherRepository;

    @BeforeEach
    @Transactional
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
    @Transactional
    @DisplayName("학생 인적 사항 저장")
    void saveStudentInformation() {

        //When
        studentService.registerStudentInformation(testStudentInformationDTO());

        //Then
        assertThat(testStudentInformationDTO().getStudentName())
                .isEqualTo(testStudentInformationDTO().getStudentName());
    }

    @Test
    @DisplayName("학생 정보 삭제(이름이 아닌 고유번호로)")
    void deleteStudentEntityById() {

        //교사정보
        TeacherEntity teacherEntity = testTeacherDataSet().toEntity();

        doReturn(teacherRepository.save(new TeacherEntity(1L, testTeacherDataSet().getUsername(), testTeacherDataSet().getName(), testTeacherDataSet().getPassword()
                , testTeacherDataSet().getEmail(), testTeacherDataSet().isApproved())));

        //힉생정보
        doReturn(studentRepository.save(testStudentInformationDTO().toEntity()));
        StudentEntity testStudentEntity = testStudentInformationDTO().toEntity();

        testStudentEntity.setTeacherEntity(teacherEntity);
        studentRepository.save(testStudentEntity);

        //When
        Optional<StudentEntity> studentEntityByStudentName = studentRepository
                .findStudentEntityByStudentNameIs(testStudentInformationDTO().getStudentName());

        studentRepository.deleteStudentEntityById(studentEntityByStudentName.get().getId());

        //Then
        assertThat(studentEntityByStudentName).isEmpty();
    }
}
