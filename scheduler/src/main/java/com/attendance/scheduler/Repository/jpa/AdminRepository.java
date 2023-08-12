package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    AdminEntity findByUsernameIs(String username);
}
