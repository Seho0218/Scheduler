package com.attendance.scheduler.admin.repository;

import com.attendance.scheduler.admin.domain.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    AdminEntity findByUsernameIs(String username);
    boolean existsByEmail(String email);
}
