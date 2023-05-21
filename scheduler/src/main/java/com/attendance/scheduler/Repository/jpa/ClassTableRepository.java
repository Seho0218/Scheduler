package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClassTableRepository extends JpaRepository<ClassEntity, String> {

    void deleteByStudentNameIn(List<String> studentName);

}
