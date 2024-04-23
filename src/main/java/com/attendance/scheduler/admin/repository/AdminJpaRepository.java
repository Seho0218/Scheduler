package com.attendance.scheduler.admin.repository;

import com.attendance.scheduler.admin.domain.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminJpaRepository extends JpaRepository<AdminEntity, Long> {

    AdminEntity findByUsernameIs(String username);
    boolean existsByEmail(String email);
}
