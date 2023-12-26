package com.attendance.scheduler.comment.repository;

import com.attendance.scheduler.comment.domain.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
}
