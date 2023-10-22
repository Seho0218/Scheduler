package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.ClassEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.attendance.scheduler.Config.TestDataSet.testStudent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ClassTableRepositoryTest {

    @Autowired
    private ClassTableRepository classTableRepository;

    //Given
    @BeforeEach
    void beforeEach(){
        classTableRepository.save(testStudent().toEntity());
    }

    @Test
    void findByStudentNameIs() {

        //When
        ClassEntity byStudentNameIs = classTableRepository
                .findByStudentNameIs(testStudent().getStudentName());

        //Then
        assertEquals(testStudent().getStudentName(), byStudentNameIs.getStudentName());
    }

    @Test
    //TODO
    void deleteByStudentName() {

        //When
        classTableRepository.deleteByStudentName(testStudent().getStudentName());

        //Then
        assertNull(testStudent().getStudentName());
    }

    @Test
    void deleteByStudentNameIn() {

        //When

        //Then
    }
}