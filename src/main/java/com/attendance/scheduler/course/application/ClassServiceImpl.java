package com.attendance.scheduler.course.application;

import com.attendance.scheduler.course.domain.ClassEntity;
import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.course.dto.StudentClassDTO;
import com.attendance.scheduler.course.repository.ClassJpaRepository;
import com.attendance.scheduler.course.repository.ClassRepository;
import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.student.dto.ClassListDTO;
import com.attendance.scheduler.student.repository.StudentJpaRepository;
import com.attendance.scheduler.teacher.dto.DeleteClassDTO;
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
        Optional<StudentEntity> studentEntity = studentJpaRepository.findStudentEntityByStudentName(studentName);

        String teacherName = studentEntity.get().getTeacherEntity().getTeacherName();
        List<StudentClassDTO> studentClassByTeacherName = classRepository.getStudentClassByTeacherName(teacherName);

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
    public synchronized void saveClassTable(ClassDTO classDTO) {

        classValidator(classDTO);
        boolean existsByStudentNameIs = studentJpaRepository.existsByStudentNameIs(classDTO.getStudentName());

        if(existsByStudentNameIs){
            Optional<StudentEntity> studentEntity = studentJpaRepository.findStudentEntityByStudentName(classDTO.getStudentName());

            ClassEntity classEntity = classDTO.toEntity();
            classEntity.setTeacherEntity(studentEntity.get().getTeacherEntity());

            classJpaRepository.save(classEntity);
        }
    }

    private void classValidator(ClassDTO classDTO) {
        boolean byStudentNameIs = classJpaRepository.existsByStudentNameIs(classDTO.getStudentName());

        if (!byStudentNameIs) {
            duplicateClassValidator(classDTO);
        }

        classJpaRepository.deleteByStudentName(classDTO.getStudentName());
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
    public void deleteClass(DeleteClassDTO deleteClassDTO) {
        classJpaRepository
                .deleteByStudentNameIn(deleteClassDTO.getDeleteClassList());
    }
}