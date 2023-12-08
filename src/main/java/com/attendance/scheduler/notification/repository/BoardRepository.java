package com.attendance.scheduler.notification.repository;

import com.attendance.scheduler.notification.dto.BoardDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    public final JPAQueryFactory queryFactory;

    public List<BoardDTO> findAllBoardList(){
        return null;
    }
}
