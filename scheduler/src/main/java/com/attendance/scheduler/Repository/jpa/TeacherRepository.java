package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

    TeacherEntity findByTeacherIdIs(String id);
    TeacherEntity findByTeacherEmail(String email);
}
