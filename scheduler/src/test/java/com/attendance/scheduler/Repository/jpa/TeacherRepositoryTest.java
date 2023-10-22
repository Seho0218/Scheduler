package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.TeacherEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.attendance.scheduler.Config.TestDataSet.teacherDTO;
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
        teacherRepository.save(teacherDTO().toEntity());
    }

    @Test
    void existsByUsername() {

        //When
        boolean existsByUsername = teacherRepository
                .existsByUsername(teacherDTO().getUsername());

        //Then
        assertTrue(existsByUsername);
    }

    @Test
    void existsByEmail() {

        //When
        boolean existsByEmail = teacherRepository
                .existsByEmail(teacherDTO().getEmail());

        //Then
        assertTrue(existsByEmail);
    }

    @Test
    void findByUsernameIs() {
        //When
        TeacherEntity byUsernameIs = teacherRepository
                .findByUsernameIs(teacherDTO().getUsername());
        //Then
        assertEquals(teacherDTO().getUsername(), byUsernameIs.getUsername());
        assertEquals(teacherDTO().getEmail(), byUsernameIs.getEmail());
    }

    @Test
    void findByEmailIs() {

        //When
        TeacherEntity byEmailIs = teacherRepository
                .findByEmailIs(teacherDTO().getEmail());
        //Then
        assertEquals(teacherDTO().getUsername(), byEmailIs.getUsername());
        assertEquals(teacherDTO().getEmail(), byEmailIs.getEmail());
    }

    @Test
    void deleteByUsernameIs() {
    }
}