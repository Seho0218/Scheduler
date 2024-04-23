package com.attendance.scheduler.course.repository;

import com.attendance.scheduler.course.domain.ClassEntity;
import com.attendance.scheduler.student.domain.StudentEntity;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClassJpaRepository extends JpaRepository<ClassEntity, Long> {

    /*
    * delete StudentName
    * */
    @Transactional
    void deleteByTeacherEntity(TeacherEntity teacherEntity);

    void deleteClassEntityByStudentEntity(StudentEntity studentEntity);
}