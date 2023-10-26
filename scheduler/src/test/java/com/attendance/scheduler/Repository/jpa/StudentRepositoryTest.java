package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.StudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.attendance.scheduler.Config.TestDataSet.sampleStudentInformationDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void beforeEach(){
        studentRepository.save(sampleStudentInformationDTO().toEntity());
    }

    @Test
    @DisplayName("학생 인적 사항 정보 저장")
    void findStudentEntityByStudentName() {
        List<StudentEntity> studentEntityByStudentName = studentRepository
                .findStudentEntityByStudentName(sampleStudentInformationDTO().getStudentName());

        assertEquals(sampleStudentInformationDTO().getStudentName(),
                studentEntityByStudentName.get(0).getStudentName());
    }

    @Test
    @DisplayName("학생 정보 삭제(이름이 아닌 고유번호로)")
    void deleteStudentEntityById() {
        List<StudentEntity> studentEntityByStudentName = studentRepository
                .findStudentEntityByStudentName(sampleStudentInformationDTO().getStudentName());

        studentRepository.deleteStudentEntityById(studentEntityByStudentName.get(0).getId());

        assertNull(studentRepository.findStudentEntityById(studentEntityByStudentName.get(0).getId()));
    }
}