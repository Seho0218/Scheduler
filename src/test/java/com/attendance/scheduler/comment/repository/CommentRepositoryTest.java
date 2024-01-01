package com.attendance.scheduler.comment.repository;

import com.attendance.scheduler.comment.dto.CommentDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.attendance.scheduler.comment.domain.entity.QCommentEntity.commentEntity;

@SpringBootTest
@RequiredArgsConstructor
class CommentRepositoryTest {

    @Autowired
    public JPAQueryFactory queryFactory;

    @Test
    void getCommentList() {
        List<CommentDTO> fetch = queryFactory
                .select(Projections.fields(CommentDTO.class,
                        commentEntity.id,
                        commentEntity.commentAuthor,
                        commentEntity.comment,
                        commentEntity.creationTimeStamp))
                .from(commentEntity)
                .where(commentEntity.noticeEntity.id.eq(1L))
                .fetch();

        System.out.println("fetch = " + fetch);
    }
}