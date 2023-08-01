package com.attendance.scheduler.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String adminId;

    private String password;

    @Builder
    public AdminEntity(Long id, String adminId, String password) {
        this.id = id;
        this.adminId = adminId;
        this.password = password;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

}
