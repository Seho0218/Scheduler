package com.attendance.scheduler.comment.repository;

import com.attendance.scheduler.comment.dto.CommentDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.attendance.scheduler.comment.domain.entity.QCommentEntity.commentEntity;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    public final JPAQueryFactory queryFactory;

    public List<CommentDTO> getCommentList(Long id) {
        return queryFactory
                .select(Projections.fields(CommentDTO.class,
                        commentEntity.id,
                        commentEntity.commentAuthor,
                        commentEntity.comment,
                        commentEntity.creationTimestamp))
                .from(commentEntity)
                .where(commentEntity.noticeEntity.id.eq(id))
                .fetch();
    }
}
