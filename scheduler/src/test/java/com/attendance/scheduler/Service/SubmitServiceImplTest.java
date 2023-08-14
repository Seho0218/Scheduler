package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class SubmitServiceImplTest {

    @Autowired
    private ClassTableRepository classTableRepository;

    @Test
    @DisplayName("학생정보가 제대로 저장이 되었는지 확인")
    void saveClassTable() {

        //given
        ClassDTO classDTO = new ClassDTO();
        classDTO.setStudentName("김김김");
        classDTO.setMonday(1);
        classDTO.setTuesday(2);
        classDTO.setWednesday(3);
        classDTO.setThursday(4);
        classDTO.setFriday(5);

        //when
        classTableRepository.save(classDTO.toEntity());

        //then
        Optional<ClassEntity> studentNameIs = classTableRepository.findByStudentNameIs("김김김");

        studentNameIs.ifPresent(student -> {
                    assertEquals("김김김", student.getStudentName());
                    assertEquals(1, student.getMonday());
                    assertEquals(2, student.getTuesday());
                    assertEquals(3, student.getWednesday());
                    assertEquals(4, student.getThursday());
                    assertEquals(5, student.getFriday());
        });
    }
}
