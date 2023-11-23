package com.attendance.scheduler.course.repository;

import com.attendance.scheduler.common.dto.LoginDTO;
import com.attendance.scheduler.infra.config.Authority.UserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static com.attendance.scheduler.config.TestDataSet.testStudentClassDataSet;
import static com.attendance.scheduler.config.TestDataSet.testTeacherDataSet;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ClassJpaRepositoryTest {

    @Autowired private ClassJpaRepository classJpaRepository;
    @Autowired private UserDetailService userDetailsService;


    //Given
    @BeforeEach
    void beforeEach(){
        classJpaRepository.save(testStudentClassDataSet().toEntity());

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(testTeacherDataSet().getUsername());

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDTO.getUsername());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(testTeacherDataSet().getUsername(),
                        null , userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void existsByStudentNameIs() {

        //When
        boolean existsByStudentNameIs = classJpaRepository
                .existsByStudentNameIs(testStudentClassDataSet().getStudentName());

        //Then
        assertTrue(existsByStudentNameIs);
    }

    @Test
    //TODO
    void deleteByStudentName() {

        //When
        classJpaRepository.deleteByStudentName(testStudentClassDataSet().getStudentName());

        //Then
        assertNull(testStudentClassDataSet().getStudentName());
    }

    @Test
    void deleteByStudentNameIn() {

        //When

        //Then
    }
}