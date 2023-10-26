package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Mapper.ClassMapper;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.ClassService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.attendance.scheduler.Config.TestDataSet.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ClassServiceImplTest {

    @Autowired
    private ClassService classService;

    @Autowired
    private ClassTableRepository classTableRepository;

    @Autowired
    private ClassMapper classMapper;

    private StudentClassDTO studentClassDTO;


    @BeforeEach
    void beforeEach(){
        testStudent();
        classService.saveClassTable(testStudent());

        test2Student();
        classService.saveClassTable(test2Student());

        studentClassDTO = new StudentClassDTO();
        studentClassDTO.setStudentName(testStudent().getStudentName());
    }

    @Test
    @DisplayName("(무작위로 호출)값이 제대로 저장되었는지 확인")
    void checkClassList() {
        List<ClassDTO> dtoList = classTableRepository.findAll()
                .stream()
                .map(classMapper::toClassDTO)
                .toList();

        ClassListDTO classListDTO = ClassListDTO.getInstance();

        for (ClassDTO classDTO : dtoList) {
            classListDTO.getMondayClassList().add(classDTO.getMonday());
            classListDTO.getTuesdayClassList().add(classDTO.getTuesday());
            classListDTO.getWednesdayClassList().add(classDTO.getWednesday());
            classListDTO.getThursdayClassList().add(classDTO.getThursday());
            classListDTO.getFridayClassList().add(classDTO.getFriday());
        }

        for (int i = 0; i < dtoList.size(); i++) {
            assertEquals(classListDTO.getMondayClassList().get(i), dtoList.get(i).getMonday());
            assertEquals(classListDTO.getTuesdayClassList().get(i), dtoList.get(i).getTuesday());
            assertEquals(classListDTO.getWednesdayClassList().get(i), dtoList.get(i).getWednesday());
            assertEquals(classListDTO.getThursdayClassList().get(i), dtoList.get(i).getThursday());
            assertEquals(classListDTO.getFridayClassList().get(i), dtoList.get(i).getFriday());
        }
    }

    @Test
    @DisplayName("(리스트 별로 호출)저장된 수강 정보를 학생 별 확인")
    void findClassByStudent() {

        //Given
        List<ClassDTO> classList = Arrays
                .asList(testStudent(), test2Student());

        //when
        List<ClassDTO> classByStudent = classService
                .findClassByStudent();

        //then
        for (int i = 0; i < classByStudent.size(); i++) {
            assertEquals(classList.get(i).getMonday(), classByStudent.get(i).getMonday());
            assertEquals(classList.get(i).getTuesday(), classByStudent.get(i).getTuesday());
            assertEquals(classList.get(i).getWednesday(), classByStudent.get(i).getWednesday());
            assertEquals(classList.get(i).getThursday(), classByStudent.get(i).getThursday());
            assertEquals(classList.get(i).getFriday(), classByStudent.get(i).getFriday());
        }
    }

    @Test
    @DisplayName("저장된 수강 정보 확인")
    void findAllClasses() {
        //when
        ClassListDTO allClasses = classService
                .findAllClasses();
        //then
        assertEquals(2, allClasses.getMondayClassList().size());
    }

    @Test
    @DisplayName("학생 수업 조회")
    void findStudentClasses() {

        //when
        StudentClassDTO studentClasses = classService
                .findStudentClasses(studentClassDTO);

        //then
        assertEquals(studentClassDTO.getStudentName(), studentClasses.getStudentName());

    }

    @Test
    @DisplayName("수업 정보 저장")
    void saveClassTable() {

        ClassEntity byStudentNameIs = classTableRepository
                .findByStudentNameIs(testStudent().getStudentName());
        assertEquals(testStudent().getStudentName(), byStudentNameIs.getStudentName());
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

        classService.saveClassTable(classDTO);

        //찾는 로직
        StudentClassDTO studentClasses = classService
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
        StudentClassDTO studentClasses = classService
                .findStudentClasses(studentClassDTO);

        //Then
        assertEquals(studentClasses.getStudentName(), studentClassDTO.getStudentName());
    }

    @Test
    @DisplayName("중복 수업 확인")
    void duplicatedClassTest() {

        //when //then
        assertThrows(IllegalStateException.class,
                () -> classService.saveClassTable(testStudent_duplicated()));
    }

    @AfterEach
    void afterEach(){
        classTableRepository.deleteByStudentName(testStudent().getStudentName());
        classTableRepository.deleteByStudentName(test2Student().getStudentName());
        classTableRepository.deleteByStudentName(testStudent_duplicated().getStudentName());
    }
}