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

    @GetMapping(value = "/login")
    public String teacherLoginForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserDetails) {
            return "redirect:/";
        }
        model.addAttribute("login", new LoginDTO());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/login/error")
    public String teacherLogin(HttpSession session, Model model){
        log.info("errorMessage = {}", session.getAttribute("errorMessage"));
        model.addAttribute("login", new LoginDTO());
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        return "login";
    }
}
