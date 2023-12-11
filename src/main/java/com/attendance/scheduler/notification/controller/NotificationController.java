package com.attendance.scheduler.notification.controller;

import com.attendance.scheduler.notification.application.NotificationService;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class NotificationController {

    public final NotificationService notificationService;

    @GetMapping("")
    public String getNoticeList(String condition, Pageable pageable, Model model){
        Page<NoticeDTO> allBoardList = notificationService.pageNoticeList(condition, pageable);
        model.addAttribute("noticeList", allBoardList);
        model.addAttribute("maxPage", 5);
        return "board/list";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/createNotice")
    public String writeNoticeForm(Model model){
        model.addAttribute("notice", new NoticeDTO());
        return "board/noticeEdit";
    }

    @PostMapping("/write")
    public  String writeNotice(NoticeDTO noticeDTO, Model model) {
        try {
            notificationService.writeNotice(noticeDTO);
            return "board/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "board/notice";
        }
    }

    //보기
    @GetMapping("/{id}")
    public String getNotice(@PathVariable Long id, Model model){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        NoticeDTO noticeById = notificationService.findNoticeById(id);
        model.addAttribute("notice", noticeById);
        model.addAttribute("author", auth.getName());
        return "board/notice";
    }

    @PostMapping("/edit/{id}")
    public String editNotice(@PathVariable Long id, Model model){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        NoticeDTO postById = notificationService.findNoticeById(id);

        if(postById.getAuthor().equals(username)){
            model.addAttribute("post", postById);
            return "board/noticeEdit";
        }
        model.addAttribute("errorMessage", "권한이 없습니다.");
        return "board/notice";
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long id){
        try{
            notificationService.deleteNotice(id);
            return ResponseEntity.ok("삭제되었습니다");
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
