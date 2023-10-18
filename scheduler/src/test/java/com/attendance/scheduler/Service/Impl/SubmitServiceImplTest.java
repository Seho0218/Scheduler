package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.SubmitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SubmitServiceImplTest {

    @Autowired
    private ClassTableRepository classTableRepository;

    String errorCode = "다른 원생과 겹치는 시간이 있습니다. 새로고침 후, 다시 신청해 주세요.";
    @Autowired
    private SubmitService submitService;

    @Test
    @BeforeEach
    @DisplayName("수업 정보 저장")
    void saveClassTable() {

        //given
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("testStudent");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);

        //when
        submitService.saveClassTable(classDTO);

        //then
        ClassEntity studentNameIs = classTableRepository.findByStudentNameIs("testStudent");

        assertEquals("testStudent", studentNameIs.getStudentName());
        assertEquals(1, studentNameIs.getMonday());
        assertEquals(2, studentNameIs.getTuesday());
        assertEquals(3, studentNameIs.getWednesday());
        assertEquals(4, studentNameIs.getThursday());
        assertEquals(5, studentNameIs.getFriday());

    }

    @Test
    @DisplayName("수업 정보 변경")
    void changeSavedClassTable() {

        //given
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("testStudent");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(2);

        //when
        submitService.saveClassTable(classDTO);

        //then
        ClassEntity studentNameIs = classTableRepository.findByStudentNameIs("testStudent");

        assertEquals("testStudent", studentNameIs.getStudentName());
        assertEquals(1, studentNameIs.getMonday());
        assertEquals(2, studentNameIs.getTuesday());
        assertEquals(3, studentNameIs.getWednesday());
        assertEquals(4, studentNameIs.getThursday());
        assertEquals(2, studentNameIs.getFriday());
    }

    @Test
    @DisplayName("중복 수업 확인")
    void duplicatedClassTest() {

        //given
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("testStudent2");
        classDTO.setMonday(1);
        classDTO.setTuesday(3);
        classDTO.setWednesday(5);
        classDTO.setThursday(5);
        classDTO.setFriday(5);

        //when //then
        assertThrows(IllegalStateException.class,
                () -> submitService.saveClassTable(classDTO),
                errorCode);
    }

    @AfterEach
    void afterEach(){
        classTableRepository.deleteByStudentName("testStudent");
        classTableRepository.deleteByStudentName("test2Student");
    }

}
