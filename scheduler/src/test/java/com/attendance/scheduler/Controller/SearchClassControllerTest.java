package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.SubmitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest @Slf4j
@RequiredArgsConstructor
class SearchClassControllerTest {

    @Autowired
    private SubmitService submitService;

    @Autowired
    private ClassTableRepository classTableRepository;

    @Test
    void modifyClass() {

        //Given
        log.info("Given start");
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("testStudent");
        classDTO.setMonday(1);
        classDTO.setTuesday(1);
        classDTO.setWednesday(1);
        classDTO.setThursday(1);
        classDTO.setFriday(1);
        submitService.saveClassTable(classDTO);
        log.info("Given end");

        log.info("When start");
        classDTO.setStudentName("testStudent");
        classDTO.setMonday(1);
        classDTO.setTuesday(1);
        classDTO.setWednesday(2);
        classDTO.setThursday(3);
        classDTO.setFriday(4);
        submitService.saveClassTable(classDTO);
        log.info("When end");

        //Then
        Assertions.assertEquals(classDTO.getMonday(), 1);
        Assertions.assertEquals(classDTO.getTuesday(),1);
        Assertions.assertEquals(classDTO.getWednesday(),2);
        Assertions.assertEquals(classDTO.getThursday(),3);
        Assertions.assertEquals(classDTO.getFriday(),4);

    }
    @AfterEach
    void afterEach(){
        classTableRepository.deleteByStudentName("testStudent");
    }
}