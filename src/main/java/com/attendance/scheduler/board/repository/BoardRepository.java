package com.attendance.scheduler.board.repository;

import com.attendance.scheduler.board.dto.BoardDTO;
import com.attendance.scheduler.board.dto.Condition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.attendance.scheduler.admin.domain.QAdminEntity.adminEntity;
import static com.attendance.scheduler.board.domain.QBoardEntity.boardEntity;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    public final JPAQueryFactory queryFactory;


    public Page<BoardDTO> pageNoticeList(Condition condition, Pageable pageable){
        List<BoardDTO> content = queryFactory
                .select(Projections.fields(BoardDTO.class,
                        boardEntity.id,
                        boardEntity.title,
                        boardEntity.content,
                        adminEntity.name,
                        boardEntity.views,
                        boardEntity.creationTimestamp,
                        boardEntity.modifiedDate))
                .from(boardEntity)
                .join(adminEntity)
                .on(boardEntity.adminEntity.id.eq(adminEntity.id))
                .where(
                        titleEq(condition.getTitleContent()),
                        contentEq(condition.getTitleContent())
                )
                .orderBy(boardEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> counts = queryFactory
                .select(boardEntity.count())
                .from(boardEntity)
                .where(
                        titleEq(condition.getTitleContent()),
                        contentEq(condition.getTitleContent())
                );

        return PageableExecutionUtils.getPage(content, pageable, counts::fetchOne);
    }

    private BooleanExpression titleEq(String title){
        return hasText(title) ? boardEntity.title.eq(title) : null;
    }

    private BooleanExpression contentEq(String content){
        return hasText(content) ? boardEntity.content.eq(content) : null;
    }

    public BoardDTO findNoticeById(Long id) {

        queryFactory
                .update(boardEntity)
                .set(boardEntity.views, boardEntity.views.add(1))
                .where(boardEntity.id.eq(id))
                .execute();

        return queryFactory
                .select(Projections.fields(BoardDTO.class,
                        boardEntity.id,
                        boardEntity.title,
                        boardEntity.content,
                        adminEntity.name,
                        boardEntity.views,
                        boardEntity.creationTimestamp))
                .from(boardEntity)
                .join(adminEntity)
                .on(boardEntity.adminEntity.id.eq(adminEntity.id))
                .where(boardEntity.id.eq(id))
                .fetchOne();
    }

    public BoardDTO editNoticeForm(Long id){
        return queryFactory
                .select(Projections.fields(BoardDTO.class,
                        boardEntity.id,
                        boardEntity.title,
                        boardEntity.content,
                        adminEntity.name,
                        boardEntity.views,
                        boardEntity.creationTimestamp,
                        boardEntity.modifiedDate))
                .from(boardEntity)
                .join(adminEntity)
                .on(boardEntity.adminEntity.id.eq(adminEntity.id))
                .where(boardEntity.id.eq(id))
                .fetchOne();

    }
}
