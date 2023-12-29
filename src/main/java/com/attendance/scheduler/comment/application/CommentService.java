package com.attendance.scheduler.comment.application;

import com.attendance.scheduler.comment.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentList(Long id);

    void saveComment(CommentDTO commentDTO);

    void deleteComment(CommentDTO commentDTO);
}
