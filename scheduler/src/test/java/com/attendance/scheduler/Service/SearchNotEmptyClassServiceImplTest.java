package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Mapper.StudentClassMapper;
import com.attendance.scheduler.Repository.jpa.SearchNotEmptyClassRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@RequiredArgsConstructor
class SearchNotEmptyClassServiceImplTest {

    @Autowired
    private final SearchNotEmptyClassRepository searchNotEmptyClassRepository;
    @Autowired
    private StudentClassMapper studentClassMapper;

    @Test
    void myTest() {

        ClassEntity sdf = searchNotEmptyClassRepository.findByStudentNameIs("sdf");

        StudentClassDTO classDTO = studentClassMapper.toClassDTO(sdf);
        String studentName = classDTO.getStudentName();


        Assertions.assertEquals("sdf", studentName);
    }
}