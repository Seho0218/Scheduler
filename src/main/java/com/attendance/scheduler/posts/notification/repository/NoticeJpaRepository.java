package com.attendance.scheduler.posts.notification.repository;

import com.attendance.scheduler.posts.notification.domain.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeJpaRepository extends JpaRepository<NoticeEntity, Long> {

    NoticeEntity findNoticeEntityById(Long id);
}
