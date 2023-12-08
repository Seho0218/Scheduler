package com.attendance.scheduler.notification.application;

import com.attendance.scheduler.notification.dto.BoardDTO;

import java.util.List;

public interface BoardService {

    List<BoardDTO> findAllBoardList();
}
