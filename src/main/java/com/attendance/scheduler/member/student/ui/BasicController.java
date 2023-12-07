package com.attendance.scheduler.member.student.ui;

import com.attendance.scheduler.course.dto.StudentClassDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BasicController {

    @GetMapping("/")
    public String basic(Model model){
        model.addAttribute("studentClassDTO", new StudentClassDTO());
        return "index";
    }
}