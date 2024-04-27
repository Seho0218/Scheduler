package com.attendance.scheduler.notification.repository;

import com.attendance.scheduler.notification.dto.NotificationDTO;
import com.attendance.scheduler.teacher.domain.TeacherEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.attendance.scheduler.notification.domain.QNotificationEntity.notificationEntity;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {

    public final JPAQueryFactory queryFactory;

    public List<NotificationDTO> findByTeacherEntityOrderByCreatedDesc(TeacherEntity teacherEntity) {
        return queryFactory
                .select(Projections.fields(NotificationDTO.class,
                        notificationEntity.message,
                        notificationEntity.checked,
                        notificationEntity.createdTime))
                .from(notificationEntity)
                .where(notificationEntity.teacherEntity.eq(teacherEntity))
                .fetch();
    }


}
