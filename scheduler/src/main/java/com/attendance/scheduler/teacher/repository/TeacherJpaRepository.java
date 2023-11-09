package com.attendance.scheduler.teacher.repository;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherJpaRepository extends JpaRepository<TeacherEntity, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    TeacherEntity findByUsernameIs(String username);

    Optional<TeacherEntity> findByEmailIs(String email);

    @Transactional
    void deleteByUsernameIs(String username);

}
