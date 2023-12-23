package com.attendance.scheduler.notification.controller;

import com.attendance.scheduler.notification.application.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/search/api/")
@RequiredArgsConstructor
public class NotificationApiController {

    public final NotificationService notificationService;

    @PreAuthorize("hasRole('ROLE_USER')")
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
