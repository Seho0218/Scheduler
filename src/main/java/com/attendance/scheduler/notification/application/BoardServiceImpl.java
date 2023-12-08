package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.notification.dto.BoardDTO;
import com.attendance.scheduler.notification.repository.BoardJpaRepository;
import com.attendance.scheduler.notification.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardJpaRepository boardJpaRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<BoardDTO> findAllBoardList() {
        return boardRepository.findAllBoardList();
    }
}
