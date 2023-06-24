package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Config.CustomAuthenticationFailureHandler;
import com.attendance.scheduler.Dto.LoginDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    //교사 로그인 폼
    @GetMapping("")
    public String loginForm(Model model) {
        model.addAttribute("login", new LoginDTO());
        return "login";
    }

    //교사 로그인
    @PostMapping("Login")
    public String teacherLogin(HttpServletRequest request, HttpServletResponse response, LoginDTO loginDTO,
                               Model model) throws ServletException, IOException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getTeacherId(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/manage/class"; // 절대경로
        } catch (AuthenticationException e) {

            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            String errorMessage = (String) request.getAttribute("errorMessage");
            model.addAttribute("errorMessage", errorMessage);
            System.out.println("errorMessage = " + errorMessage);
            return "login";
        }
    }

    //관리자 로그인 폼
    @GetMapping("adminLogin")
    public String adminLoginForm(Model model) {
        model.addAttribute("login", new LoginDTO());
        return "adminLogin";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}
