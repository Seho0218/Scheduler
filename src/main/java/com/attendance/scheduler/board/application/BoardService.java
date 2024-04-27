package com.attendance.scheduler.board.application;

import com.attendance.scheduler.board.dto.BoardDTO;
import com.attendance.scheduler.board.dto.Condition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {


    Page<BoardDTO> pageNoticeList(Condition condition, Pageable pageable);

    void writeNotice(BoardDTO boardDTO);

    BoardDTO findNoticeById(Long id);

    BoardDTO editNoticeForm(Long id);

    void editNotice(BoardDTO boardDTO);

    void deleteNotice(Long id);
}
