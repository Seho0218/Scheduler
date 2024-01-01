package com.attendance.scheduler.comment.dto;

import com.attendance.scheduler.comment.domain.entity.CommentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter @Setter
@ToString
@NoArgsConstructor
public class CommentDTO {

    private Long noticeId;
    private Long commentId;
    private String commentAuthor;
    private String password;
    private String comment;
    private Timestamp creationTimeStamp;

    public CommentEntity toEntity() {
        return CommentEntity.builder()
                .commentAuthor(commentAuthor)
                .comment(comment)
                .creationTimeStamp(creationTimeStamp)
                .build();
    }
}
