package com.attendance.scheduler.Notification.application;

import com.attendance.scheduler.Notification.domain.NotificationEntity;
import com.attendance.scheduler.Notification.dto.NotificationDTO;
import com.attendance.scheduler.Notification.repository.NotificationJpaRepository;
import com.attendance.scheduler.Notification.repository.NotificationRepository;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationJpaRepository notificationJpaRepository;


    public void markAsRead(List<NotificationEntity> notificationEntity) {
        notificationEntity.forEach(NotificationEntity::checked);
    }

    @Override
    public List<NotificationDTO> findByTeacherEntityOrderByCreatedDesc(TeacherEntity teacherEntity) {
        return notificationRepository.findByTeacherEntityOrderByCreatedDesc(teacherEntity);
    }

    @Override
    public void CheckedByTeacherEntity(TeacherEntity teacherEntity, Long id) {
        NotificationEntity notificationEntity = notificationJpaRepository
                .findNotificationEntityByTeacherEntityAndId(teacherEntity, id);
        notificationEntity.checked();
        notificationJpaRepository.save(notificationEntity);
    }
}
