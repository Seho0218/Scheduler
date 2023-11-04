package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.course.repository.ClassTableRepository;
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
class ClassTableRepositoryTest {

    @Autowired
    private ClassTableRepository classTableRepository;

    //Given
    @BeforeEach
    void beforeEach(){
        classTableRepository.save(testStudentClassDataSet().toEntity());
    }

    @Test
    void existsByStudentNameIs() {

        //When
        boolean existsByStudentNameIs = classTableRepository
                .existsByStudentNameIs(testStudentClassDataSet().getStudentName());

        //Then
        assertTrue(existsByStudentNameIs);
    }

    @Test
    //TODO
    void deleteByStudentName() {

        //When
        classTableRepository.deleteByStudentName(testStudentClassDataSet().getStudentName());

        //Then
        assertNull(testStudentClassDataSet().getStudentName());
    }

    @Test
    void deleteByStudentNameIn() {

        //When

        //Then
    }
}