package com.attendance.scheduler.comment.controller;

import com.attendance.scheduler.comment.application.CommentService;
import com.attendance.scheduler.student.application.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/comment/")
@RequiredArgsConstructor
public class CommentController {

    public final CommentService commentService;
    public final StudentService studentService;




}
