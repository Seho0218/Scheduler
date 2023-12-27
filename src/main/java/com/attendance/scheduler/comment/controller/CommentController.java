package com.attendance.scheduler.comment.controller;

import com.attendance.scheduler.comment.application.CommentService;
import com.attendance.scheduler.comment.dto.CommentDTO;
import com.attendance.scheduler.student.application.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/comment/")
@RequiredArgsConstructor
public class CommentController {

    public final CommentService commentService;
    public final StudentService studentService;

    @PostMapping("/")
    public ResponseEntity<String> grantAuth(CommentDTO commentDTO) {

        return ResponseEntity.ok("승인되었습니다.");
    }




}
