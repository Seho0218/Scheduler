package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.AdminDTO;
import com.attendance.scheduler.Dto.Teacher.LoginTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private static final String loginError = "아이디 또는 비밀번호를 확인해주세요.";
    private static final String approved = "관리자의 승인을 기다려주세요";

    //로그인
    private final LoginService loginService;

    //교사 로그인 폼
    @GetMapping("")
    public String loginForm(Model model) {
        model.addAttribute("login", new TeacherDTO());
        return "login";
    }

    //교사 로그인
    @PostMapping("Login")
    public String teacherLogin(@Validated @ModelAttribute("login") LoginTeacherDTO loginTeacherDTO, BindingResult bindingResult,
                               HttpSession httpSession, Model model){

        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            TeacherDTO loginTeacher = loginService.loginTeacher(loginTeacherDTO);
            if(loginTeacher.isApproved()) {
                httpSession.setAttribute("logId", loginTeacher.getTeacherId());
                httpSession.setAttribute("logName", loginTeacher.getTeacherName());
                httpSession.setAttribute("ROLE", loginTeacher.getRole());
                return "redirect:/manage/class"; // 절대경로
            }
            model.addAttribute("login", new TeacherDTO());
            model.addAttribute("approved", approved);
            return "login";
        } catch (Exception e) {
            model.addAttribute("login", new TeacherDTO());
            model.addAttribute("loginError", loginError);
            return "login";
        }
    }

    //관리자 로그인 폼
    @GetMapping("adminLogin")
    public String adminLoginForm(Model model) {
        model.addAttribute("login", new AdminDTO());
        return "adminLogin";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:index";
    }
}
