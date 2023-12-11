package com.attendance.scheduler.notification.repository;

import com.attendance.scheduler.notification.dto.NoticeDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.attendance.scheduler.notification.domain.notice.QNoticeEntity.noticeEntity;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {

    public final JPAQueryFactory queryFactory;


    public Page<NoticeDTO> pageNoticeList(String condition, Pageable pageable){

        List<NoticeDTO> content = queryFactory
                .select(Projections.fields(NoticeDTO.class,
                        noticeEntity.id,
                        noticeEntity.title,
                        noticeEntity.type,
                        noticeEntity.content,
                        noticeEntity.author,
                        noticeEntity.creationTimestamp,
                        noticeEntity.modifiedDate))
                .from(noticeEntity)
//                .where(
//                        noticeEntity.title.eq(condition),
//                        noticeEntity.content.eq(condition)
//                )
                .orderBy(noticeEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> counts = queryFactory
                .select(noticeEntity.count())
                .from(noticeEntity);
//                .where(
//                        noticeEntity.title.eq(condition),
//                        noticeEntity.content.eq(condition)
//                );


        return PageableExecutionUtils.getPage(content, pageable, counts::fetchOne);
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
