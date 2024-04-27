package com.attendance.scheduler.notification.repository;

import com.attendance.scheduler.notification.domain.NotificationEntity;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {
    NotificationEntity findNotificationEntityByTeacherEntityAndId(TeacherEntity teacherEntity, Long id);

}
