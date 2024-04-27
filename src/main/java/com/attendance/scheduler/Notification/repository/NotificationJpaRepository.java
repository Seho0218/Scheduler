package com.attendance.scheduler.Notification.repository;

import com.attendance.scheduler.Notification.domain.NotificationEntity;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {
    NotificationEntity findNotificationEntityByTeacherEntityAndId(TeacherEntity teacherEntity, Long id);

}
