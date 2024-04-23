package com.attendance.scheduler.common;

import com.attendance.scheduler.common.dto.LoginDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    //교사 로그인 폼
    @GetMapping("/login")
    public String teacherLoginForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return "redirect:/";
        }
        model.addAttribute("login", new LoginDTO());
        return "login";
    }

    //관리자 로그인 폼
    @GetMapping("/adminLogin")
    public String adminLoginForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails) {
            return "redirect:/";
        }
        model.addAttribute("login", new LoginDTO());
        return "adminLogin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    //교사 로그인
    @GetMapping("/login/error")
    public String teacherLogin(HttpSession session, Model model){
        log.info("errorMessage = {}", session.getAttribute("errorMessage"));
        model.addAttribute("login", new LoginDTO());
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        return "login";
    }

    //관리자 로그인
    @GetMapping("/adminLogin/error")
    public String adminLogin(HttpSession session, Model model){
        log.info("errorMessage = {}", session.getAttribute("errorMessage"));
        model.addAttribute("login", new LoginDTO());
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        return "adminLogin";
    }
}
