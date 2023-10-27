package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.TeacherEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.attendance.scheduler.Config.TestDataSet.testTeacherDataSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @BeforeEach
    void beforeEachTest(){
        teacherRepository.save(testTeacherDataSet().toEntity());
    }

    @Test
    void existsByUsername() {

        //When
        boolean existsByUsername = teacherRepository
                .existsByUsername(testTeacherDataSet().getUsername());

        //Then
        assertTrue(existsByUsername);
    }

    @Test
    void existsByEmail() {

        //When
        boolean existsByEmail = teacherRepository
                .existsByEmail(testTeacherDataSet().getEmail());

        //Then
        assertTrue(existsByEmail);
    }

    @Test
    void findByUsernameIs() {
        //When
        TeacherEntity byUsernameIs = teacherRepository
                .findByUsernameIs(testTeacherDataSet().getUsername());
        //Then
        assertEquals(testTeacherDataSet().getUsername(), byUsernameIs.getUsername());
        assertEquals(testTeacherDataSet().getEmail(), byUsernameIs.getEmail());
    }

    @Test
    void findByEmailIs() {

        //When
        TeacherEntity byEmailIs = teacherRepository
                .findByEmailIs(testTeacherDataSet().getEmail());
        //Then
        assertEquals(testTeacherDataSet().getUsername(), byEmailIs.getUsername());
        assertEquals(testTeacherDataSet().getEmail(), byEmailIs.getEmail());
    }

    @Test
    void deleteByUsernameIs() {
    }
}