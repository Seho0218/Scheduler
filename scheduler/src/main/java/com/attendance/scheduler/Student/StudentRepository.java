package com.attendance.scheduler.Student;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findStudentEntityByStudentNameIs(String StudentName);

    Optional<StudentEntity> findStudentEntityById(Long id);

    @EntityGraph(attributePaths = {"classEntity"})
    List<StudentEntity> findAll();

    @Transactional
    void deleteStudentEntityById(long id);
}