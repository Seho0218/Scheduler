package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.SearchClassService;
import com.attendance.scheduler.Service.SubmitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.attendance.scheduler.Config.TestDataSet.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SubmitServiceImplTest {

    @Autowired
    private SubmitService submitService;

    @Autowired
    private SearchClassService searchClassService;

    @Autowired
    private ClassTableRepository classTableRepository;

    private StudentClassDTO studentClassDTO;

    @BeforeEach
    void beforeEach(){
        testStudent();
        submitService.saveClassTable(testStudent());

        test2Student();
        submitService.saveClassTable(test2Student());

        studentClassDTO = new StudentClassDTO();
        studentClassDTO.setStudentName(testStudent().getStudentName());
    }

    @Test
    void saveClassTable() {
    }

    @Test
    @DisplayName("수업 변경 확인")
    void modifyClass(){

        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName(testStudent().getStudentName());
        classDTO.setMonday(1);
        classDTO.setTuesday(1);
        classDTO.setWednesday(2);
        classDTO.setThursday(3);
        classDTO.setFriday(4);

        submitService.saveClassTable(classDTO);

        //찾는 로직
        StudentClassDTO studentClasses = searchClassService
                .findStudentClasses(studentClassDTO);

        assertEquals(studentClasses.getMonday(), 1);
        assertEquals(studentClasses.getTuesday(),1);
        assertEquals(studentClasses.getWednesday(),2);
        assertEquals(studentClasses.getThursday(),3);
        assertEquals(studentClasses.getFriday(),4);
    }


    @Test
    @DisplayName("학생 수업 저장 상태 확인")
    void checkStudentClasses() {

        //When
        StudentClassDTO studentClasses = searchClassService
                .findStudentClasses(studentClassDTO);

        //Then
        assertEquals(studentClasses.getStudentName(), studentClassDTO.getStudentName());
    }

    @Test
    @DisplayName("중복 수업 확인")
    void duplicatedClassTest() {

        //when //then
        assertThrows(IllegalStateException.class,
                () -> submitService.saveClassTable(testStudent_duplicated()));
    }

    @AfterEach
    void afterEach(){
        classTableRepository.deleteByStudentName(testStudent().getStudentName());
        classTableRepository.deleteByStudentName(test2Student().getStudentName());
        classTableRepository.deleteByStudentName(testStudent_duplicated().getStudentName());
    }
}
