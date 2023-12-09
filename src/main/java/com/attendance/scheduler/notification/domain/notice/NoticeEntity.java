package com.attendance.scheduler.notification.domain.notice;

import com.attendance.scheduler.member.admin.domain.AdminEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Table(name = "notice")
@NoArgsConstructor(access = PROTECTED)
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author;

    @Enumerated(EnumType.STRING)
    private NoticeType type;

    @CreationTimestamp
    private Timestamp creationTimestamp;

    @UpdateTimestamp
    private Timestamp modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminId")
    private AdminEntity adminEntity;

    public void setAdminEntity(AdminEntity adminEntity) {
        if (this.adminEntity != null) {
            this.adminEntity.getNoticeEntityList().remove(this);
        }
        this.adminEntity = adminEntity;
        if(adminEntity != null){
            adminEntity.setBoardEntity(this);
        }
    }

    @Builder
    public NoticeEntity(Long id, String title, String content, String author, NoticeType type, Timestamp creationTimestamp, Timestamp modifiedDate, AdminEntity adminEntity) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.type = type;
        this.creationTimestamp = creationTimestamp;
        this.modifiedDate = modifiedDate;
        this.adminEntity = adminEntity;
    }
}
