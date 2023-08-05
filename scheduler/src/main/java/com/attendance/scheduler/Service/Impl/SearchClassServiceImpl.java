package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Mapper.ClassMapper;
import com.attendance.scheduler.Mapper.StudentClassMapper;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.SearchClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchClassServiceImpl implements SearchClassService {

    private final ClassTableRepository classTableRepository;
    private final StudentClassMapper studentClassMapper;

    private final ClassMapper classMapper;

    @Override
    public List<ClassDTO> findClassTable() {

        return classTableRepository.findAll()
                .stream()
                .map(classMapper::toClassDTO)
                .collect(Collectors.toList());

    }

    @Override
    public ClassListDTO findClassesOrderByAsc() {

        List<ClassDTO> classDTOS = classTableRepository.findAll()
                .stream()
                .map(classMapper::toClassDTO)
                .collect(Collectors.toList());

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

        ClassListDTO classListDTO = ClassListDTO.getInstance();

        classListDTO.setMondayClassList(monday);
        classListDTO.setTuesdayClassList(tuesday);
        classListDTO.setWednesdayClassList(wednesday);
        classListDTO.setThursdayClassList(thursday);
        classListDTO.setFridayClassList(friday);

        return classListDTO;
    }

    @Override
    public StudentClassDTO findStudentClasses(StudentClassDTO studentClassDTO) {
        String studentName = studentClassDTO.getStudentName().trim();
        ClassEntity byStudentNameIs = classTableRepository.findByStudentNameIs(studentName);
        return studentClassMapper.toClassDTO(byStudentNameIs);
    }
}

