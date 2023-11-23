package com.attendance.scheduler.teacher.repository;

import com.attendance.scheduler.member.teacher.domain.TeacherEntity;
import com.attendance.scheduler.member.teacher.repository.TeacherJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.attendance.scheduler.config.TestDataSet.testTeacherDataSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TeacherJpaRepositoryTest {

    @Autowired
    private TeacherJpaRepository teacherJpaRepository;

    @BeforeEach
    void beforeEachTest(){
        teacherJpaRepository.save(testTeacherDataSet().toEntity());
    }

    @Test
    void existsByUsername() {

        //When
        boolean existsByUsername = teacherJpaRepository
                .existsByUsername(testTeacherDataSet().getUsername());

        //Then
        assertTrue(existsByUsername);
    }

    @Test
    void existsByEmail() {

        //When
        boolean existsByEmail = teacherJpaRepository
                .existsByEmail(testTeacherDataSet().getEmail());

        //Then
        assertTrue(existsByEmail);
    }

    @Test
    void findByUsernameIs() {
        //When
        TeacherEntity byUsernameIs = teacherJpaRepository
                .findByUsernameIs(testTeacherDataSet().getUsername());
        //Then
        assertEquals(testTeacherDataSet().getUsername(), byUsernameIs.getUsername());
        assertEquals(testTeacherDataSet().getEmail(), byUsernameIs.getEmail());
    }

    @Test
    void findByEmailIs() {

        //When
        Optional<TeacherEntity> byEmailIs = teacherJpaRepository
                .findByEmailIs(testTeacherDataSet().getEmail());
        //Then
        if(byEmailIs.isPresent()) {
            assertEquals(testTeacherDataSet().getUsername(), byEmailIs.get().getUsername());
            assertEquals(testTeacherDataSet().getEmail(), byEmailIs.get().getEmail());
        }
    }

    @Test
    void deleteByUsernameIs() {
    }
}