package com.attendance.scheduler.member.admin.domain;

import com.attendance.scheduler.member.admin.dto.EditEmailDTO;
import jakarta.persistence.*;
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
@Table(name = "admin")
@NoArgsConstructor(access = PROTECTED)
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "adminId")
    private String username;

    private String password;

    @Column(columnDefinition = "varchar(255) default '이메일을 입력해 주세요'")
    private String email;

    @Builder
    public AdminEntity(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateEmail(EditEmailDTO editEmailDTO) {
        this.email = editEmailDTO.getEmail();
    }
}
