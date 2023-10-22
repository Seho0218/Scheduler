package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.TeacherEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    TeacherEntity findByUsernameIs(String username);
    TeacherEntity findByEmailIs(String email);

    @Transactional
    void deleteByUsernameIs(String username);
}
