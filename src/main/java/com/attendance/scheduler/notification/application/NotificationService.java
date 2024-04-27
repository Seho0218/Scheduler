package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.notification.domain.NotificationEntity;
import com.attendance.scheduler.notification.dto.NotificationDTO;
import com.attendance.scheduler.teacher.domain.TeacherEntity;

import java.util.List;

public interface NotificationService {
    void markAsRead(List<NotificationEntity> notificationEntity);


    List<NotificationDTO> findByTeacherEntityOrderByCreatedDesc(TeacherEntity teacherEntity);
    void CheckedByTeacherEntity(TeacherEntity teacherEntity, Long id);
}
