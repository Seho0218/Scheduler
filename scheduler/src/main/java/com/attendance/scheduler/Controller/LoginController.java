package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.LoginDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    //교사 로그인 폼
    @GetMapping("")
    public String loginForm(Model model) {
        model.addAttribute("login", new LoginDTO());
        return "login";
    }

    //교사 로그인
    @GetMapping("Login")
    public String teacherLogin(HttpSession session, Model model){
        System.out.println("session = " + session.getAttribute("errorMessage"));
        model.addAttribute("login", new LoginDTO());
        model.addAttribute("errorMessage", session.getAttribute("errorMessage"));
        return "login";

    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}
