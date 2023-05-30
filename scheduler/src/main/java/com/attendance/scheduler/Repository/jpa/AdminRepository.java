package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    //아이디 중복 체크
    int countByAdminIdIs(String adminId);

    AdminEntity findAdminEntityByAdminEmail(String adminEmail);
    AdminEntity findAdminEntityByAdminId(String adminId);

    @Query("SELECT 1 FROM AdminEntity u WHERE u.adminId = :adminId AND u.adminEmail = :adminEmail")
    String checkUserExists(@Param("adminId") String adminId, @Param("adminEmail") String adminEmail);
}
