package com.attendance.scheduler.board.application;

import com.attendance.scheduler.admin.domain.AdminEntity;
import com.attendance.scheduler.admin.repository.AdminJpaRepository;
import com.attendance.scheduler.board.domain.BoardEntity;
import com.attendance.scheduler.board.dto.BoardDTO;
import com.attendance.scheduler.board.dto.Condition;
import com.attendance.scheduler.board.repository.BoardJpaRepository;
import com.attendance.scheduler.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardJpaRepository boardJpaRepository;
    private final BoardRepository boardRepository;
    private final AdminJpaRepository adminJpaRepository;

    @Override
    public Page<BoardDTO> pageNoticeList(Condition condition, Pageable pageable) {
        return boardRepository.pageNoticeList(condition, pageable);
    }

    @Override
    @Transactional
    public void writeNotice(BoardDTO boardDTO) {
        AdminEntity admin = adminJpaRepository.findByUsernameIs(boardDTO.getName());
        BoardEntity entity = boardDTO.toEntity();
        entity.setAdminEntity(admin);
        boardJpaRepository.save(entity);
    }

    @Override
    @Transactional
    public BoardDTO findNoticeById(Long id) {
        return boardRepository.findNoticeById(id);
    }

    @Override
    public BoardDTO editNoticeForm(Long id) {
        return boardRepository.editNoticeForm(id);
    }

    @Override
    @Transactional
    public void editNotice(BoardDTO boardDTO) {
        BoardEntity boardEntityById = boardJpaRepository.findBoardEntityById(boardDTO.getId());
        boardEntityById.updateTitle(boardDTO.getTitle());
        boardEntityById.updateContent(boardDTO.getContent());
        boardJpaRepository.save(boardEntityById);
    }

    @Override
    @Transactional
    public void deleteNotice(Long id) {
        boardJpaRepository.deleteById(id);
    }
}
