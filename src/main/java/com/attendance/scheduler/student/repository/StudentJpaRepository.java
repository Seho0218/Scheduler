package com.attendance.scheduler.student.repository;

import com.attendance.scheduler.student.domain.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentJpaRepository extends JpaRepository<StudentEntity, Long> {

    boolean existsByStudentNameIs(String StudentName);

    StudentEntity findStudentEntityById(Long id);


    StudentEntity findStudentEntityByStudentName(String studentName);

    @Transactional
    void deleteStudentEntityById(long id);
}