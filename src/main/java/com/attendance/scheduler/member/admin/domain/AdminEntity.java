package com.attendance.scheduler.member.admin.domain;

import com.attendance.scheduler.member.admin.dto.EditEmailDTO;
import com.attendance.scheduler.notification.domain.notice.NoticeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Table(name = "admin")
@NoArgsConstructor(access = PROTECTED)
public class AdminEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "admin_id")
    private Long id;

    private String name;

    private String username;

    private String password;

    @Column(columnDefinition = "varchar(255) default '이메일을 입력해 주세요'")
    private String email;

    @Builder
    public AdminEntity(Long id, String name, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @OneToMany(mappedBy = "adminEntity")
    List<NoticeEntity> noticeEntityList = new ArrayList<>();


    public void setBoardEntity(NoticeEntity noticeEntity) {
        this.noticeEntityList.add(noticeEntity);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateEmail(EditEmailDTO editEmailDTO) {
        this.email = editEmailDTO.getEmail();
    }
}
