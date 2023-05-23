package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Repository.jpa.SearchNotEmptyClassRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SearchNotEmptyClassServiceImplTest {

    @Autowired
    private final SearchNotEmptyClassRepository searchNotEmptyClassRepository;

    @Test
    void myTest() {

        StudentClassDTO sdf = searchNotEmptyClassRepository.findByStudentName("sdf");
        String studentName = sdf.getStudentName();
        Assertions.assertEquals("sdf", studentName);
    }
}