package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.AdminDTO;
import com.attendance.scheduler.Service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminController {

    public final AdminService adminService;

    /*
     * adminLogin Form
     * */
    @GetMapping("")
    public String loginForm(Model model) {
        model.addAttribute("login", new AdminDTO());
        return "admin/adminLogin";
    }

    /*
     * TeacherList
     * */
    @GetMapping("teacherList")
    public String teacherList(Model model) {
        model.addAttribute("teacherList", adminService.findAllTeacherAccount());
        return "admin/teacherList";
    }

    /*
     * TODO grant Teacher Auth
     * */
    @PostMapping("grant")
    public ResponseEntity<String> grantAuth(Model model) {

        return ResponseEntity.ok("승인되었습니다.");
    }
}
