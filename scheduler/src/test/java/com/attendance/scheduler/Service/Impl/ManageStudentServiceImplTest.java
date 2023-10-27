package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Dto.StudentInformationDTO;
import com.attendance.scheduler.Entity.StudentEntity;
import com.attendance.scheduler.Entity.TeacherEntity;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Repository.jpa.StudentRepository;
import com.attendance.scheduler.Repository.jpa.TeacherRepository;
import com.attendance.scheduler.Service.ClassService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.attendance.scheduler.Config.TestDataSet.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ManageStudentServiceImplTest {

    @Autowired
    private ClassService classService;

    @Autowired
    private ClassTableRepository classTableRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @BeforeEach
    void beforeEach(){
        testStudent();
        classService.saveClassTable(testStudent());

        test2Student();
        classService.saveClassTable(test2Student());

        StudentClassDTO studentClassDTO = new StudentClassDTO();
        studentClassDTO.setStudentName(testStudent().getStudentName());
    }

    @Test
    @DisplayName("학생 인적 사항 저장")
    @Transactional
    void saveStudentInformation() {
        StudentInformationDTO studentInformationDTO = testStudentInformationDTO();
        TeacherEntity byUsernameIs = teacherRepository.findByUsernameIs("sampleTeacher");

        studentInformationDTO.toEntity().setTeacherEntity(byUsernameIs);
        byUsernameIs.getStudentEntityList().add(studentInformationDTO.toEntity());
        studentRepository.save(studentInformationDTO.toEntity());

        List<StudentEntity> studentEntityByStudentName = studentRepository
                .findStudentEntityByStudentName(studentInformationDTO.getStudentName());

       assertEquals(byUsernameIs.getUsername(), studentEntityByStudentName.get(0).getTeacherEntity().getUsername());


    }

    @Test
    @DisplayName("학생 정보 삭제(이름이 아닌 고유번호로)")
    void deleteStudentEntityById() {
        List<StudentEntity> studentEntityByStudentName = studentRepository
                .findStudentEntityByStudentName(testStudentInformationDTO().getStudentName());

        studentRepository.deleteStudentEntityById(studentEntityByStudentName.get(0).getId());

        assertNull(studentRepository.findStudentEntityById(studentEntityByStudentName.get(0).getId()));
    }

    @AfterEach
    void afterEach(){
        classTableRepository.deleteByStudentName(testStudent().getStudentName());
        classTableRepository.deleteByStudentName(test2Student().getStudentName());
        classTableRepository.deleteByStudentName(testStudent_duplicated().getStudentName());
    }
}
