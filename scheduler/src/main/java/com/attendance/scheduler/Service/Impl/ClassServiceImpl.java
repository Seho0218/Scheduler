package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Dto.Teacher.DeleteClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Entity.StudentEntity;
import com.attendance.scheduler.Mapper.ClassMapper;
import com.attendance.scheduler.Mapper.StudentClassMapper;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Repository.jpa.StudentRepository;
import com.attendance.scheduler.Service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassTableRepository classTableRepository;
    private final StudentClassMapper studentClassMapper;
    private final ClassMapper classMapper;
    private final StudentRepository studentRepository;

    @Override
    public List<ClassDTO> findClassByStudent() {
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
                .toList();

        ClassListDTO classListDTO = ClassListDTO.getInstance();

        for (ClassDTO classDTO : classDTOS) {
            classListDTO.getMondayClassList().add(classDTO.getMonday());
            classListDTO.getTuesdayClassList().add(classDTO.getTuesday());
            classListDTO.getWednesdayClassList().add(classDTO.getWednesday());
            classListDTO.getThursdayClassList().add(classDTO.getThursday());
            classListDTO.getFridayClassList().add(classDTO.getMonday());
        }
        return classListDTO;
    }

    @Override
    public StudentClassDTO findStudentClasses(StudentClassDTO studentClassDTO) {
        String studentName = studentClassDTO.getStudentName().trim();
        ClassEntity byStudentNameIs = classTableRepository
                .findByStudentNameIs(studentName);
        return studentClassMapper.toClassDTO(byStudentNameIs);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public synchronized void saveClassTable(ClassDTO classDTO) {
        classValidator(classDTO);
        List<StudentEntity> entityByStudentName = studentRepository
                .findStudentEntityByStudentName(classDTO.getStudentName());
        ClassEntity entity = classDTO.toEntity();
        entity.setStudentEntity(entityByStudentName.get(0));
        classTableRepository.save(entity);
    }

    private void classValidator(ClassDTO classDTO) {
        Optional<ClassEntity> byStudentNameIs =
                Optional.ofNullable(classTableRepository
                        .findByStudentNameIs(classDTO.getStudentName()));

        if (byStudentNameIs.isEmpty()) {
            duplicateClassValidator(classDTO);
        }

        classTableRepository.deleteByStudentName(classDTO.getStudentName());
        duplicateClassValidator(classDTO);
    }

    private void duplicateClassValidator(ClassDTO classDTO) {
        String errorCode = "다른 원생과 겹치는 시간이 있습니다. 새로고침 후, 다시 신청해 주세요.";


        List<ClassDTO> allClassDTO = classTableRepository.findAll()
                .stream()
                .map(classMapper::toClassDTO)
                .toList();

        for (ClassDTO classDTOList: allClassDTO) {
            Integer mondayValue = classDTOList.getMonday();
            Integer tuesdayValue = classDTOList.getTuesday();
            Integer wednesdayValue = classDTOList.getWednesday();
            Integer thursdayValue = classDTOList.getThursday();
            Integer fridayValue = classDTOList.getFriday();

            if (mondayValue.equals(classDTO.getMonday())) throw new IllegalStateException(errorCode);
            if (tuesdayValue.equals(classDTO.getTuesday())) throw new IllegalStateException(errorCode);
            if (wednesdayValue.equals(classDTO.getWednesday())) throw new IllegalStateException(errorCode);
            if (thursdayValue.equals(classDTO.getThursday())) throw new IllegalStateException(errorCode);
            if (fridayValue.equals(classDTO.getFriday())) throw new IllegalStateException(errorCode);
        }
    }

    @Override
    public void deleteClass(DeleteClassDTO deleteClassDTO) {
        classTableRepository
                .deleteByStudentNameIn(deleteClassDTO.getDeleteClassList());
    }
}