package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.DeleteClassDTO;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private ClassTableRepository classTableRepository;

    @Test
    @Transactional
    void deleteTest() {
        DeleteClassDTO deleteClassDTO = new DeleteClassDTO();
        deleteClassDTO.setDeleteClassList(Collections.singletonList("김김김"));

//        long l = classTableRepository.deleteByStudentNameIn(deleteClassDTO.getDeleteClassList());
//        assertEquals(0,l);

    }
}