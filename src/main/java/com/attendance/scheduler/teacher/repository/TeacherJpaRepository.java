package com.attendance.scheduler.teacher.repository;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherJpaRepository extends JpaRepository<TeacherEntity, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    TeacherEntity findByUsernameIs(String username);

    TeacherEntity findTeacherEntityById(Long id);

    TeacherEntity findByEmailIs(String email);

    @Transactional
    void deleteByUsernameIs(String username);

}
