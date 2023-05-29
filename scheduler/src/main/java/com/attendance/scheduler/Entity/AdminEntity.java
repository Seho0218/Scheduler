package com.attendance.scheduler.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
public class AdminEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long Id;

    private String adminId;

    private String adminPassword;

    private String adminEmail;

    @Builder
    public AdminEntity( String adminId, String adminPassword, String adminEmail) {
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.adminEmail = adminEmail;
    }
}
