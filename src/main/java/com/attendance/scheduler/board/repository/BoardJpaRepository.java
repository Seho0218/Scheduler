package com.attendance.scheduler.board.repository;

import com.attendance.scheduler.board.domain.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {

    BoardEntity findBoardEntityById(Long id);
}
