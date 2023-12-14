package com.attendance.scheduler.teacher.application;

import com.attendance.scheduler.common.dto.LoginDTO;
import com.attendance.scheduler.infra.config.security.Authority.UserDetailService;
import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.repository.StudentJpaRepository;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.repository.TeacherJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.attendance.scheduler.config.TestDataSet.testStudentInformationDTO;
import static com.attendance.scheduler.config.TestDataSet.testTeacherDataSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@Transactional
class TeacherServiceImplTest {

    @Autowired private TeacherService teacherService;
    @Autowired private UserDetailService userDetailsService;
    @Autowired private TeacherJpaRepository teacherJpaRepository;
    @Autowired private StudentJpaRepository studentJpaRepository;
    @Autowired EntityManager entityManager;


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
        teacherJpaRepository.deleteByUsernameIs(testTeacherDataSet().getUsername());
        boolean duplicateTeacherId = teacherService.findDuplicateTeacherID(testTeacherDataSet());
        assertFalse(duplicateTeacherId);
    }

    @Test
    @DisplayName("교사가 학생 정보를 등록")
    void registerStudentInformation() {

        //Given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(testTeacherDataSet().getUsername());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(testTeacherDataSet().getUsername(), null , userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Optional<StudentEntity> studentEntityByStudentNameIs = studentJpaRepository
                .findStudentEntityByStudentName(testStudentInformationDTO().getStudentName());

        //When
        if(studentEntityByStudentNameIs.isEmpty()){
            teacherService.registerStudentInformation(testStudentInformationDTO());
        }

        //Then
        if (studentEntityByStudentNameIs.isPresent()) {
            String studentName = studentEntityByStudentNameIs.get().getStudentName();
            assertThat(testStudentInformationDTO().getStudentName()).isEqualTo(studentName);
        }
    }

    @Test
    @Transactional
    @DisplayName("학생 인적 사항 저장")
    void saveStudentInformation() {

        //Given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(testTeacherDataSet().getUsername());

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(testTeacherDataSet().getUsername(),
                        null , userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        //When
        teacherService.registerStudentInformation(testStudentInformationDTO());

        //Then
        assertThat(testStudentInformationDTO().getStudentName())
                .isEqualTo(testStudentInformationDTO().getStudentName());
    }

    @Test
    @DisplayName("학생 정보 삭제(이름이 아닌 고유번호로)")
    void deleteStudentEntityById() {

        //교사정보
        TeacherEntity teacherEntity = testTeacherDataSet().toEntity();


        //힉생정보
        doReturn(studentJpaRepository.save(testStudentInformationDTO().toEntity()));
        StudentEntity testStudentEntity = testStudentInformationDTO().toEntity();

        testStudentEntity.setTeacherEntity(teacherEntity);
        studentJpaRepository.save(testStudentEntity);

        //When
        Optional<StudentEntity> studentEntityByStudentName = studentJpaRepository
                .findStudentEntityByStudentName(testStudentInformationDTO().getStudentName());

        studentJpaRepository.deleteStudentEntityById(studentEntityByStudentName.get().getId());

        //Then
        assertThat(studentEntityByStudentName).isEmpty();
    }
}