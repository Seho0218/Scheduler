package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.AdminDTO;
import com.attendance.scheduler.Dto.LoginDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    //교사 로그인 폼
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("login", new LoginDTO());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:index";
    }

    //교사 로그인
    @GetMapping("/login/error")
    public String teacherLogin(HttpSession session, Model model){
        log.info("errorMessage = {}", session.getAttribute("errorMessage"));
        model.addAttribute("login", new LoginDTO());
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        return "login";
    }

    /*
     * adminLogin Form
     * */
    @GetMapping("adminLogin")
    public String adminLoginForm(Model model) {
        model.addAttribute("login", new AdminDTO());
        return "adminLogin";
    }

    @GetMapping("/adminError")
    public String adminLogin(HttpSession session, Model model) {
        log.info("errorMessage = {}", session.getAttribute("errorMessage"));
        model.addAttribute("login", new AdminDTO());
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        return "adminLogin";
    }
}
