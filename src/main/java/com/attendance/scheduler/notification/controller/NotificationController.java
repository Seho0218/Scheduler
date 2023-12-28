package com.attendance.scheduler.notification.controller;

import com.attendance.scheduler.comment.application.CommentService;
import com.attendance.scheduler.comment.dto.CommentDTO;
import com.attendance.scheduler.notification.application.NotificationService;
import com.attendance.scheduler.notification.dto.Condition;
import com.attendance.scheduler.notification.dto.NoticeDTO;
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
public class NotificationController {

    public final NotificationService notificationService;
    public final CommentService commentService;

    @GetMapping("")
    public String getNoticeList(Condition condition, Pageable pageable, Model model){
        Page<NoticeDTO> allBoardList = notificationService.pageNoticeList(condition, pageable);
        model.addAttribute("noticeList", allBoardList);
        model.addAttribute("maxPage", 5);
        return "board/list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/createNotice")
    public String writeNoticeForm(Model model){
        model.addAttribute("noticeObject", new NoticeDTO());
        return "board/createNotice";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/write")
    public  String writeNotice(NoticeDTO noticeDTO, Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            noticeDTO.setName(auth.getName());
            notificationService.writeNotice(noticeDTO);
            return "redirect:/board";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/createNotice";
        }
    }

    @GetMapping("/{id}")
    public String noticeForm(@PathVariable("id") Long id, Model model){
        Optional<NoticeDTO> noticeById = Optional.ofNullable(notificationService.findNoticeById(id));
        List<CommentDTO> commentList = commentService.getCommentList();
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

        Optional<NoticeDTO> noticeById = Optional.ofNullable(notificationService.editNoticeForm(id));
        if(noticeById.isPresent()) {
            model.addAttribute("noticeObject", new NoticeDTO());
            model.addAttribute("notice", noticeById.get());
            return "board/editNotice";
        }
        return "redirect:/board";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/edit/")
    public String editNotice(@RequestParam(name = "id") Long id, NoticeDTO noticeDTO){
        noticeDTO.setId(id);
        notificationService.editNotice(noticeDTO);
        return "redirect:/board";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/")
    public String deleteNotice(@RequestParam(name = "id") Long id){
        notificationService.deleteNotice(id);
        return "redirect:/board";
    }
}
