package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.Account.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveStudentRepository extends JpaRepository<StudentEntity, Long> {
}
