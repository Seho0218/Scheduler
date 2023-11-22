package com.attendance.scheduler.member.student.repository;

import com.attendance.scheduler.member.student.domain.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StudentJpaRepository extends JpaRepository<StudentEntity, Long> {

    boolean existsByStudentNameIs(String StudentName);

    Optional<StudentEntity> findStudentEntityById(Long id);


    Optional<StudentEntity> findStudentEntityByStudentName(String studentName);

    @Transactional
    void deleteStudentEntityById(long id);
}