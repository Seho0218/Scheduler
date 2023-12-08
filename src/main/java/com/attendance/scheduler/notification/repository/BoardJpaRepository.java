package com.attendance.scheduler.notification.repository;

import com.attendance.scheduler.notification.domain.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {
}
