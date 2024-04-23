package com.attendance.scheduler.notification.repository;

import com.attendance.scheduler.notification.domain.notice.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeJpaRepository extends JpaRepository<NoticeEntity, Long> {

    NoticeEntity findNoticeEntityById(Long id);
}
