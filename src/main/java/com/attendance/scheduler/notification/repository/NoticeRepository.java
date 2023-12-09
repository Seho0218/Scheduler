package com.attendance.scheduler.notification.repository;

import com.attendance.scheduler.notification.dto.NoticeDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.attendance.scheduler.notification.domain.notice.QNoticeEntity.noticeEntity;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {

    public final JPAQueryFactory queryFactory;

    public List<NoticeDTO> findAllNoticeList(){
        return queryFactory
                .select(Projections.fields(NoticeDTO.class,
                        noticeEntity.id,
                        noticeEntity.title,
                        noticeEntity.type,
                        noticeEntity.content,
                        noticeEntity.author,
                        noticeEntity.creationTimestamp,
                        noticeEntity.modifiedDate))
                .from(noticeEntity)
                .fetch();
    }

    public NoticeDTO findPostById(Long id) {
        return queryFactory
                .select(Projections.fields(NoticeDTO.class,
                        noticeEntity.id,
                        noticeEntity.title,
                        noticeEntity.type,
                        noticeEntity.content,
                        noticeEntity.author,
                        noticeEntity.creationTimestamp,
                        noticeEntity.modifiedDate))
                .from(noticeEntity)
                .where(noticeEntity.id.eq(id))
                .fetchOne();
    }
}
