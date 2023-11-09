package com.attendance.scheduler.course;

import com.attendance.scheduler.course.repository.ClassJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.attendance.scheduler.config.TestDataSet.testStudentClassDataSet;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ClassJpaRepositoryTest {

    @Autowired
    private ClassJpaRepository classJpaRepository;

    //Given
    @BeforeEach
    void beforeEach(){
        classJpaRepository.save(testStudentClassDataSet().toEntity());
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