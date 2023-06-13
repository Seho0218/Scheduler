package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Service.SubmitService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class BasicControllerTest {

    @Autowired
    private final SubmitService submitService;

    @Test
    void submitForm() {
        //given
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("김김김");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);

        //when
        submitService.saveClassTable(classDTO);

        //then


    }
}
