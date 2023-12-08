package com.attendance.scheduler.notification.domain;

import com.attendance.scheduler.member.admin.domain.AdminEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Table(name = "board")
@NoArgsConstructor(access = PROTECTED)
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "boardId")
    private Long id;

    private String title;
    private String content;
    private String author;

    private String type;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private AdminEntity adminEntity;

    public void setAdminEntity(AdminEntity adminEntity) {
        if (this.adminEntity != null) {
            this.adminEntity.getBoardEntityList().remove(this);
        }
        this.adminEntity = adminEntity;
        if(adminEntity != null){
            adminEntity.setBoardEntity(this);
        }
    }
}
