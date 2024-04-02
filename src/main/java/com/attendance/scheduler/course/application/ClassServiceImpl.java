package com.attendance.scheduler.course.application;

import com.attendance.scheduler.course.domain.ClassEntity;
import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.course.dto.StudentClassDTO;
import com.attendance.scheduler.course.repository.ClassJpaRepository;
import com.attendance.scheduler.course.repository.ClassRepository;
import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.dto.ClassListDTO;
import com.attendance.scheduler.student.repository.StudentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassJpaRepository classJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final ClassRepository classRepository;

    @Override
    @Transactional
    public List<ClassDTO> findStudentClassList() {
        return classRepository.getStudentClassList();
    }

    @Override
    @Transactional
    public ClassListDTO findTeachersClasses(String studentName) {

        //학생 이름으로
        StudentEntity studentEntity = studentJpaRepository.findStudentEntityByStudentName(studentName);

        List<StudentClassDTO> studentClassByTeacherName = classRepository.getStudentClassByTeacherEntity(studentEntity.getTeacherEntity());

        ClassListDTO classListDTO = ClassListDTO.getInstance();

        classListDTO.setStudentName(studentName);
        for (StudentClassDTO classDTO : studentClassByTeacherName) {
            classListDTO.getMondayClassList().add(classDTO.getMonday());
            classListDTO.getTuesdayClassList().add(classDTO.getTuesday());
            classListDTO.getWednesdayClassList().add(classDTO.getWednesday());
            classListDTO.getThursdayClassList().add(classDTO.getThursday());
            classListDTO.getFridayClassList().add(classDTO.getFriday());
        }
        return classListDTO;
    }

    @Override
    public Optional<StudentClassDTO> findStudentClasses(String studentName) {
        return Optional.ofNullable(classRepository.getStudentClassByStudentName(studentName));
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveClassTable(ClassDTO classDTO) throws InterruptedException {

        while(true) {
            try {
                classValidator(classDTO);
                boolean existsByStudentNameIs = studentJpaRepository.existsByStudentNameIs(classDTO.getStudentName());
                if (existsByStudentNameIs) {
                    StudentEntity studentEntity = studentJpaRepository.findStudentEntityByStudentName(classDTO.getStudentName());

                    ClassEntity classEntity = classDTO.toEntity();
                    classEntity.setTeacherEntity(studentEntity.getTeacherEntity());
                    classEntity.setStudentEntity(studentEntity);
                    classJpaRepository.save(classEntity);
                    break;
                }
            }catch (Exception e){
                Thread.sleep(50);
            }
        }
    }

    private void classValidator(ClassDTO classDTO) {
        Optional<StudentEntity> studentEntityByStudentName = Optional.ofNullable(studentJpaRepository.findStudentEntityByStudentName(classDTO.getStudentName()));

        if (studentEntityByStudentName.isEmpty()) {
            duplicateClassValidator(classDTO);
        }

        classJpaRepository.deleteById(studentEntityByStudentName.get().getId());
        duplicateClassValidator(classDTO);
    }

    private void duplicateClassValidator(ClassDTO classDTO) {
        String errorCode = "다른 원생과 겹치는 시간이 있습니다. 새로고침 후, 다시 신청해 주세요.";

        List<ClassDTO> allClassDTO = classRepository.getStudentClassList();

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
    @Transactional
    public void deleteClass(String studentName) {
        StudentEntity studentEntity = studentJpaRepository.findStudentEntityByStudentName(studentName);
        classJpaRepository.deleteClassEntityByStudentEntity(studentEntity);
    }
}