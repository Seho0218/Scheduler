package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Repository.jpa.ClassSaveRepository;
import com.attendance.scheduler.Repository.jpa.SearchNotEmptyClassRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SubmitServiceImplTest {

    @Autowired
    private ClassSaveRepository classSaveRepository;

    @Autowired
    private SearchNotEmptyClassRepository searchNotEmptyClassRepository;

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

        classValidator(classDTO);

        //when
        classSaveRepository.save(classDTO.toEntity());

        //then
        ClassEntity studentNameIs = searchNotEmptyClassRepository.findByStudentNameIs("김김김");

        assertEquals("김김김", studentNameIs.getStudentName());
        assertEquals(1, studentNameIs.getMonday());
        assertEquals(2, studentNameIs.getTuesday());
        assertEquals(3, studentNameIs.getWednesday());
        assertEquals(4, studentNameIs.getThursday());
        assertEquals(5, studentNameIs.getFriday());
    }

        private void classValidator(ClassDTO classDTO) {
            Integer monday = classDTO.getMonday();
            Integer tuesday = classDTO.getTuesday();
            Integer wednesday = classDTO.getWednesday();
            Integer thursday = classDTO.getThursday();
            Integer friday = classDTO.getFriday();

            List<Object[]> classesOrderByAsc = searchNotEmptyClassRepository.findClassesOrderByAsc();

            for (Object[] row : classesOrderByAsc) {
                if(monday.equals(row[0])){
                    throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
                }
                if(tuesday.equals(row[1])){
                    throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
                }
                if(wednesday.equals(row[2])){
                    throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
                }
                if(thursday.equals(row[3])){
                    throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
                }
                if(friday.equals(row[4])){
                    throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
                }
            }
    }
}
