package com.attendance.scheduler.notification.repository;

import com.attendance.scheduler.notification.domain.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<NoticeEntity, Long> {
}
