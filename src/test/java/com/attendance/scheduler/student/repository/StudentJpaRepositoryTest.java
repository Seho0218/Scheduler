package com.attendance.scheduler.student.repository;

import com.attendance.scheduler.member.student.repository.StudentJpaRepository;
import com.attendance.scheduler.member.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.attendance.scheduler.config.TestDataSet.testStudentInformationDTO;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
class StudentJpaRepositoryTest {

    @Autowired private StudentJpaRepository studentJpaRepository;
    @Autowired private StudentRepository studentRepository;

    @BeforeEach
    void beforeEach(){
        studentJpaRepository.save(testStudentInformationDTO().toEntity());
    }

//    @Test
    @DisplayName("학생 인적 사항 정보 저장")
    void findStudentEntityByStudentName() {
//        boolean existsByStudentNameIs = studentRepository.getStudentEntity(testStudentInformationDTO().getStudentName());
//
//        if(existsByStudentNameIs){
//            assertThat(testStudentInformationDTO().getStudentName()).isEqualTo(studentEntity.getStudentName()));
//        }
    }

    @Test //TODO
    @DisplayName("학생 정보 찾기(고유번호로)")
    void findStudentEntityById(){

    }

    @Test
    @DisplayName("학생 정보 삭제(이름이 아닌 고유번호로)")
    void deleteStudentEntityById() {
//        Optional<StudentEntity> studentEntityByStudentName = studentJpaRepository
//                .existsByStudentNameIs(testStudentInformationDTO().getStudentName());
//
//        studentEntityByStudentName.ifPresent(studentEntity -> studentJpaRepository.deleteStudentEntityById(studentEntity.getId()));
//
//        assertNull(studentJpaRepository.findStudentEntityById(studentEntityByStudentName.get().getId()));
    }
}