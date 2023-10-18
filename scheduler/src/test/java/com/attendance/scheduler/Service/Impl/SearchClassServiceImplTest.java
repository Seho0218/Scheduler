package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Mapper.ClassMapper;
import com.attendance.scheduler.Mapper.StudentClassMapper;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.SubmitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SearchClassServiceImplTest {

    @Autowired
    private SubmitService submitService;

    @Autowired
    private ClassTableRepository classTableRepository;

    @Autowired
    private StudentClassMapper studentClassMapper;

    @Autowired
    private ClassMapper classMapper;


    @BeforeEach
    void beforeEach(){
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("testStudent");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);

        submitService.saveClassTable(classDTO);

        ClassDTO classDTO2 = new ClassDTO();
        classDTO2.setStudentName("test2Student");
        classDTO2.setMonday(2);
        classDTO2.setTuesday(3);
        classDTO2.setWednesday(4);
        classDTO2.setThursday(5);
        classDTO2.setFriday(6);

        submitService.saveClassTable(classDTO2);
    }

    @Test
    void findClassesOrderByAsc() {

        List<ClassDTO> classDTOS = classTableRepository.findAll()
                .stream()
                .map(classMapper::toClassDTO)
                .toList();

        List<Integer> monday = new ArrayList<>();
        List<Integer> tuesday = new ArrayList<>();
        List<Integer> wednesday = new ArrayList<>();
        List<Integer> thursday = new ArrayList<>();
        List<Integer> friday = new ArrayList<>();

        for (ClassDTO classDTO : classDTOS) {
            monday.add(classDTO.getMonday());
            tuesday.add(classDTO.getTuesday());
            wednesday.add(classDTO.getWednesday());
            thursday.add(classDTO.getThursday());
            friday.add(classDTO.getFriday());
        }
        for (int i = 0; i < classDTOS.size(); i++) {
            Assertions.assertEquals(monday.get(i) , classDTOS.get(i).getMonday());
            Assertions.assertEquals(tuesday.get(i) , classDTOS.get(i).getTuesday());
            Assertions.assertEquals(wednesday.get(i) , classDTOS.get(i).getWednesday());
            Assertions.assertEquals(thursday.get(i) , classDTOS.get(i).getThursday());
            Assertions.assertEquals(friday.get(i) , classDTOS.get(i).getFriday());
        }
    }


    @Test
    void findStudentClasses() {

        //Given
        StudentClassDTO studentClassDTO = new StudentClassDTO();
        studentClassDTO.setStudentName("testStudent");
        String studentName = studentClassDTO.getStudentName().trim();

        //When
        ClassEntity byStudentNameIs = classTableRepository.findByStudentNameIs(studentName);

        //Then
        StudentClassDTO classDTO = studentClassMapper.toClassDTO(byStudentNameIs);
        Assertions.assertEquals(classDTO.getStudentName(), studentClassDTO.getStudentName());
    }

    @AfterEach
    void afterEach(){
        classTableRepository.deleteByStudentName("testStudent");
        classTableRepository.deleteByStudentName("test2Student");
    }
}