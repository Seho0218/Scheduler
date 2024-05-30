package com.attendance.scheduler.comment.domain.entity;

import com.attendance.scheduler.board.domain.BoardEntity;
import com.attendance.scheduler.student.domain.StudentEntity;
import jakarta.persistence.*;
import lombok.Builder;
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
@NoArgsConstructor(access = PROTECTED)
public class CommentEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String commentAuthor;
    private String comment;

    @CreationTimestamp
    private Timestamp creationTimeStamp;

    @ManyToOne(fetch = LAZY)
    private BoardEntity boardEntity;

    public void setBoardEntity(BoardEntity boardEntity) {
        if (this.boardEntity != null) {
            this.boardEntity.getCommentEntityList().remove(this);
        }
        this.boardEntity = boardEntity;
        if (boardEntity != null) {
            boardEntity.setCommentEntity(this);
        }
    }

    @ManyToOne(fetch = LAZY)
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

    @Builder
    public CommentEntity(String commentAuthor, String comment, Timestamp creationTimeStamp) {
        this.commentAuthor = commentAuthor;
        this.comment = comment;
        this.creationTimeStamp = creationTimeStamp;
    }
}
