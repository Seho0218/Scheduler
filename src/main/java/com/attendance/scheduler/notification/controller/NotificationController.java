package com.attendance.scheduler.notification.controller;

import com.attendance.scheduler.notification.application.NotificationService;
import com.attendance.scheduler.notification.dto.Condition;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class NotificationController {

    public final NotificationService notificationService;

    @GetMapping("")
    public String getNoticeList(Condition condition, Pageable pageable, Model model){
        Page<NoticeDTO> allBoardList = notificationService.pageNoticeList(condition, pageable);
        model.addAttribute("noticeList", allBoardList);
        model.addAttribute("maxPage", 5);
        return "board/list";
    }

    @GetMapping("/createNotice")
    public String writeNoticeForm(Model model){
        model.addAttribute("noticeObject", new NoticeDTO());
        return "board/createNotice";
    }

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
        Optional<NoticeDTO> noticeById = notificationService.findNoticeById(id);
        if(noticeById.isPresent()) {
            model.addAttribute("notice", noticeById.get());
            return "board/notice";
        }
        return "redirect:/board";
    }

    @GetMapping("/edit/")
    public String editNoticeForm(@RequestParam(name = "id") Long id, Model model) {

        Optional<NoticeDTO> noticeById = notificationService.findNoticeById(id);
        if(noticeById.isPresent()) {
            model.addAttribute("noticeObject", new NoticeDTO());
            model.addAttribute("notice", noticeById.get());
            return "board/editNotice";
        }
        return "redirect:/board";
    }

    @PostMapping("/edit/")
    public String editNotice(@RequestParam(name = "id") Long id, NoticeDTO noticeDTO){
        noticeDTO.setId(id);
        notificationService.editNotice(noticeDTO);
        return "redirect:/board";
    }

}
