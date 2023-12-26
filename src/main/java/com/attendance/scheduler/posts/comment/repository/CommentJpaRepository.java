package com.attendance.scheduler.posts.comment.repository;

import com.attendance.scheduler.posts.comment.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
}
