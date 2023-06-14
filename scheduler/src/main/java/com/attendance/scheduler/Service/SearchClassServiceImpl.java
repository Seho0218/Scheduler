package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Mapper.ClassMapper;
import com.attendance.scheduler.Mapper.StudentClassMapper;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Repository.jpa.SearchNotEmptyClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchClassServiceImpl implements SearchClassService {

    private final SearchNotEmptyClassRepository searchNotEmptyClassRepository;
    private final StudentClassMapper studentClassMapper;

    private final ClassTableRepository classTableRepository;
    private final ClassMapper classMapper;

    @Override
    public List<ClassDTO> findClassTable() {

        List<ClassEntity> classEntityList = classTableRepository.findAll();

        return classEntityList.stream()
                .map(classMapper::toClassDTO)
                .collect(Collectors.toList());
    }

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

        ClassListDTO classListDTO = ClassListDTO.getInstance();

        classListDTO.setClassInMondayList(monday);
        classListDTO.setClassInTuesdayList(tuesday);
        classListDTO.setClassInWednesdayList(wednesday);
        classListDTO.setClassInThursdayList(thursday);
        classListDTO.setClassInFridayList(friday);

        return classListDTO;
    }

    @Override
    public StudentClassDTO findStudentClasses(StudentClassDTO studentClassDTO) {
        String studentName = studentClassDTO.getStudentName().trim();
        ClassEntity byStudentNameIs = searchNotEmptyClassRepository.findByStudentNameIs(studentName);
        return studentClassMapper.toClassDTO(byStudentNameIs);
    }
}

