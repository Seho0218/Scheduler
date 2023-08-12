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

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

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
    public ClassListDTO findAllClasses() {

        List<ClassDTO> classDTOS = classTableRepository.findAll()
                .stream()
                .map(classMapper::toClassDTO)
                .collect(Collectors.toList());

        ClassListDTO classListDTO = ClassListDTO.getInstance();

        for (ClassDTO classDTO : classDTOS) {
            classListDTO.setMondayClassList(singletonList(classDTO.getMonday()));
            classListDTO.setTuesdayClassList(singletonList(classDTO.getTuesday()));
            classListDTO.setWednesdayClassList(singletonList(classDTO.getWednesday()));
            classListDTO.setThursdayClassList(singletonList(classDTO.getThursday()));
            classListDTO.setFridayClassList(singletonList(classDTO.getFriday()));
        }
        return classListDTO;
    }

    @Override
    public StudentClassDTO findStudentClasses(StudentClassDTO studentClassDTO) {
        String studentName = studentClassDTO.getStudentName().trim();
        ClassEntity byStudentNameIs = classTableRepository.findByStudentNameIs(studentName);
        return studentClassMapper.toClassDTO(byStudentNameIs);
    }
}

