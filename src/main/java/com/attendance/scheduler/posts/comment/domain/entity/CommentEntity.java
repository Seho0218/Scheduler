package com.attendance.scheduler.posts.comment.domain.entity;

import com.attendance.scheduler.posts.notification.domain.notice.NoticeEntity;
import com.attendance.scheduler.member.student.domain.StudentEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Table(name = "comment")
@NoArgsConstructor(access = PROTECTED)
public class CommentEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String commentAuthor;

    private String comment;

    private String password;


    @CreationTimestamp
    private Timestamp creationTimestamp;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "notice_id")
    private NoticeEntity noticeEntity;

    public void setNoticeEntity(NoticeEntity noticeEntity) {
        if (this.noticeEntity != null) {
            this.noticeEntity.getCommentEntityList().remove(this);
        }
        this.noticeEntity = noticeEntity;
        if (noticeEntity != null) {
            noticeEntity.setCommentEntity(this);
        }
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private StudentEntity studentEntity;

    public void setStudentEntity(StudentEntity studentEntity) {
        if (this.studentEntity != null) {
            this.studentEntity.getCommentEntityList().remove(this);
        }
        this.studentEntity = studentEntity;
        if (studentEntity != null) {
            studentEntity.addCommentEntity(this);
        }
    }
}
