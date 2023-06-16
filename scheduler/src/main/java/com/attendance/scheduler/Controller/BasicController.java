package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.AdminDTO;
import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Dto.Teacher.LoginTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Service.SearchClassService;
import com.attendance.scheduler.Service.SubmitService;
import com.attendance.scheduler.Service.TeacherService;
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
@RequestMapping("/")
@RequiredArgsConstructor
public class BasicController {

    private final SubmitService submitService;
    private final SearchClassService searchClassService;

    private static final String loginError = "아이디 또는 비밀번호를 확인해주세요.";
    private static final String approved = "관리자의 승인을 기다려주세요";

    //로그인
    private final TeacherService teacherService;

    @GetMapping("/")
    public String basic(Model model){

        getClassList(model);
        model.addAttribute("class", new ClassDTO());
        return "index";
    }

    //교사 로그인 폼
    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("login", new TeacherDTO());
        return "login";
    }

    //교사 로그인
    @PostMapping("teacherLogin")
    public String teacherLogin(@Validated @ModelAttribute("login") LoginTeacherDTO loginTeacherDTO, BindingResult bindingResult,
                               HttpSession httpSession, Model model){

        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            TeacherDTO loginTeacher = teacherService.loginTeacher(loginTeacherDTO);
            if(loginTeacher.isApproved()) {
                httpSession.setAttribute("logId", loginTeacher.getTeacherId());
                httpSession.setAttribute("logName", loginTeacher.getTeacherName());
                return "admin/manage";
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
        return "index";
    }

    //  수업 조회 폼
    @GetMapping("search")
    public String searchClass(Model model) {
        model.addAttribute("studentClass", new StudentClassDTO());
        return "search";
    }

    //  제출
    @PostMapping("submit")
    public String submitForm(@Validated @ModelAttribute("class") ClassDTO classDTO,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            getClassList(model);
            log.info("errors={}", bindingResult);
            return "index";
        }

        try {
            submitService.saveClassTable(classDTO);
            return "completion";
        }catch (Exception e){
            getClassList(model);
            return "index";
        }
    }

//  제출 완료 폼
    @GetMapping("completion")
    public String completeForm() {
        return "completion";
    }

    private void getClassList(Model model) {

        ClassListDTO classesOrderByAsc = searchClassService.findClassesOrderByAsc();

        model.addAttribute("classInMondayList", classesOrderByAsc.getClassInMondayList());
        model.addAttribute("classInTuesdayList", classesOrderByAsc.getClassInTuesdayList());
        model.addAttribute("classInWednesdayList", classesOrderByAsc.getClassInWednesdayList());
        model.addAttribute("classInThursdayList", classesOrderByAsc.getClassInThursdayList());
        model.addAttribute("classInFridayList", classesOrderByAsc.getClassInFridayList());

        log.info("monday = {}", classesOrderByAsc.getClassInMondayList());
        log.info("tuesday = {}", classesOrderByAsc.getClassInTuesdayList());
        log.info("wednesday = {}", classesOrderByAsc.getClassInWednesdayList());
        log.info("thursday = {}", classesOrderByAsc.getClassInThursdayList());
        log.info("friday = {}", classesOrderByAsc.getClassInFridayList());
    }
}