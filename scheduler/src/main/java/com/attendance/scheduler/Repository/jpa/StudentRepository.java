package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    List<StudentEntity> findStudentEntityByStudentName(String StudentName);
    List<StudentEntity> findStudentEntityById(Long id);

    @Transactional
    void deleteStudentEntityById(long id);
}