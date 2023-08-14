package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

    Optional<TeacherEntity> findByUsernameIs(String username);
    Optional<TeacherEntity> findByEmailIs(String email);

    void deleteByUsernameIs(String username);
}
