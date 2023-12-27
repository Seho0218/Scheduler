package com.attendance.scheduler.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class CommentDTO {

    private String username;
    private String password;
    private String comment;
}
