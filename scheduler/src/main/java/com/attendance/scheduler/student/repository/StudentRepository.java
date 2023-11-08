package com.attendance.scheduler.student.repository;

import com.attendance.scheduler.student.domain.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findStudentEntityByStudentNameIs(String StudentName);

    Optional<StudentEntity> findStudentEntityById(Long id);

    @Transactional
    void deleteStudentEntityById(long id);
}