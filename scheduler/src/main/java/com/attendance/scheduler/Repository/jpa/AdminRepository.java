package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    Optional<AdminEntity> findByUsernameIs(String username);
}
