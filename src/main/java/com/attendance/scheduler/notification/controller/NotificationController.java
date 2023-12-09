package com.attendance.scheduler.notification.controller;

import com.attendance.scheduler.notification.application.NotificationService;
import com.attendance.scheduler.notification.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class NotificationController {

    public final NotificationService notificationService;

    @GetMapping("")
    public String getNoticeList(Model model){
        List<NoticeDTO> allBoardList = notificationService.findAllNoticeList();
        model.addAttribute("noticeList", allBoardList);
        return "board/list";
    }

    @GetMapping("/createNotice")
    public String writeNoticeForm(Model model){
        model.addAttribute("notice", new NoticeDTO());
        return "board/noticeEdit";
    }

    @PostMapping("/createNotice")
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
        NoticeDTO noticeById = notificationService.findNoticeById(id);
        model.addAttribute("notificationList", noticeById);
        return "board/notice";
    }

    @PostMapping("/edit/{id}")
    public String editNotice(@PathVariable Long id, Model model){
        try{
            NoticeDTO postById = notificationService.findNoticeById(id);
            model.addAttribute("post", postById);
            return "board/noticeEdit";
        }catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "board/notice";
        }
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
