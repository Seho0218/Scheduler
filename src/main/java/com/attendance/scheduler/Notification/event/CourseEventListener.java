package com.attendance.scheduler.Notification.event;

import com.attendance.scheduler.Notification.domain.NotificationEntity;
import com.attendance.scheduler.Notification.repository.NotificationJpaRepository;
import com.attendance.scheduler.course.event.CourseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class CourseEventListener {

    private final NotificationJpaRepository notificationJpaRepository;

    @EventListener
    public void handleCourseEvent(CourseEvent courseEvent) {

        NotificationEntity entity = NotificationEntity.builder()
                .teacherEntity(courseEvent.getTeacherEntity())
                .message(courseEvent.getMessage())
                .createdTime(LocalDateTime.now())
                .checked(false)
                .build();
        entity.setTeacherEntity(courseEvent.getTeacherEntity());

        notificationJpaRepository.save(entity);
    }
}
