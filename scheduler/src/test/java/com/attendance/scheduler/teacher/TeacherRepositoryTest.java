package com.attendance.scheduler.teacher;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.attendance.scheduler.teacher.repository.TeacherRepository;
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
        Optional<TeacherEntity> byEmailIs = teacherRepository
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