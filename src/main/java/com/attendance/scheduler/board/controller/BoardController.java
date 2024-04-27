package com.attendance.scheduler.board.controller;

import com.attendance.scheduler.board.application.BoardService;
import com.attendance.scheduler.board.dto.BoardDTO;
import com.attendance.scheduler.board.dto.Condition;
import com.attendance.scheduler.comment.application.CommentService;
import com.attendance.scheduler.comment.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    public final BoardService boardService;
    public final CommentService commentService;

    @GetMapping("")
    public String getNoticeList(Condition condition, Pageable pageable, Model model){
        Page<BoardDTO> allBoardList = boardService.pageNoticeList(condition, pageable);
        model.addAttribute("noticeList", allBoardList);
        model.addAttribute("maxPage", 5);
        return "board/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/createNotice")
    public String writeNoticeForm(Model model){
        model.addAttribute("noticeObject", new BoardDTO());
        return "board/createNotice";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/write")
    public  String writeNotice(BoardDTO boardDTO, Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            boardDTO.setName(auth.getName());
            boardService.writeNotice(boardDTO);
            return "redirect:/board";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/createNotice";
        }
    }

    @GetMapping("/{id}")
    public String noticeForm(@PathVariable("id") Long id, Model model){
        Optional<BoardDTO> noticeById = Optional.ofNullable(boardService.findNoticeById(id));
        List<CommentDTO> commentList = commentService.getCommentList(id);
        System.out.println("commentList = " + commentList);
        if(noticeById.isPresent()) {
            model.addAttribute("notice", noticeById.get());
            model.addAttribute("commentList", commentList);
            return "board/notice";
        }
        return "redirect:/board";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit/")
    public String editNoticeForm(@RequestParam(name = "id") Long id, Model model) {

        Optional<BoardDTO> noticeById = Optional.ofNullable(boardService.editNoticeForm(id));
        if(noticeById.isPresent()) {
            model.addAttribute("noticeObject", new BoardDTO());
            model.addAttribute("notice", noticeById.get());
            return "board/editNotice";
        }
        return "redirect:/board";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/edit/")
    public String editNotice(@RequestParam(name = "id") Long id, BoardDTO boardDTO){
        boardDTO.setId(id);
        boardService.editNotice(boardDTO);
        return "redirect:/board";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/")
    public String deleteNotice(@RequestParam(name = "id") Long id){
        boardService.deleteNotice(id);
        return "redirect:/board";
    }
}
