package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Repository.jpa.SearchNotEmptyClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchNotEmptyClassServiceImpl implements SearchNotEmptyClassService {

    private final SearchNotEmptyClassRepository searchNotEmptyClassRepository;

    @Override
    public ClassListDTO findClassesOrderByAsc() {

        List<Object[]> classesOrderByAsc = searchNotEmptyClassRepository.findClassesOrderByAsc();

        List<Integer> monday = new ArrayList<>();
        List<Integer> tuesday = new ArrayList<>();
        List<Integer> wednesday = new ArrayList<>();
        List<Integer> thursday = new ArrayList<>();
        List<Integer> friday = new ArrayList<>();

        for (Object[] row : classesOrderByAsc) {
            Integer mondayValue = (Integer) row[0];
            monday.add(mondayValue);

            Integer tuesdayValue = (Integer) row[1];
            tuesday.add(tuesdayValue);

            Integer wednesdayValue = (Integer) row[2];
            wednesday.add(wednesdayValue);

            Integer thursdayValue = (Integer) row[3];
            thursday.add(thursdayValue);

            Integer fridayValue = (Integer) row[4];
            friday.add(fridayValue);
        }

        ClassListDTO classListDTO = ClassListDTO.classTableInstance();

        classListDTO.setClassInMondayList(monday);
        classListDTO.setClassInTuesdayList(tuesday);
        classListDTO.setClassInWednesdayList(wednesday);
        classListDTO.setClassInThursdayList(thursday);
        classListDTO.setClassInFridayList(friday);

        return classListDTO;
    }

    @Override
    public StudentClassDTO findStudentClasses(StudentClassDTO studentClassDTO) {
        return searchNotEmptyClassRepository.findByStudentName(studentClassDTO.getStudentName());
    }
}

