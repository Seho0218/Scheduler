package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.TeacherEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    TeacherEntity findByUsernameIs(String username);

    Optional<TeacherEntity> findTeacherEntityById(Long id);
    Optional<TeacherEntity> findByEmailIs(String email);

    @Transactional
    void deleteByUsernameIs(String username);
}
