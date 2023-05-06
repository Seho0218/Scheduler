package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveClassRepository extends JpaRepository<ClassEntity, Long> {


}
