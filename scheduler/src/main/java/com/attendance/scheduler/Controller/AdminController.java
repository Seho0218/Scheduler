package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.DeleteClassDTO;
import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Service.AdminService;
import com.attendance.scheduler.Service.SearchClassService;
import com.attendance.scheduler.Service.TeacherService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminController {

    // 관리자 로직
    private final AdminService adminService;

    //수업 조회
    private final SearchClassService searchClassService;

    private static final String loginError = "아이디 또는 비밀번호를 확인해주세요";
    private static final String approved = "관리자의 승인을 기다려주세요";
    //로그인
    private final TeacherService teacherService;

    //교사 로그인
    @PostMapping("teacherLogin")
    public String teacherLogin(TeacherDTO teacherDTO, HttpSession httpSession, Model model){

        TeacherDTO loginTeacher = teacherService.loginTeacher(teacherDTO);

        if (loginTeacher == null) {
            model.addAttribute("loginError", loginError);
            return "search";
        }
        if(loginTeacher.isApproved()) {
            httpSession.setAttribute("logId", loginTeacher.getTeacherId());
            httpSession.setAttribute("logName", loginTeacher.getTeacherName());
            return "admin/manage";
        }

        model.addAttribute("approved", approved);
        model.addAttribute("login", new TeacherDTO());
        return "login";
    }

    // 조회
    @GetMapping("manage")
    public String adminPage(Model model){

        List<ClassDTO> classTable = searchClassService.findClassTable();
        model.addAttribute("classList", new DeleteClassDTO());
        model.addAttribute("findClassTable", classTable);
        log.info("student = {}", searchClassService.findClassTable());
        return "admin/manage";
    }

    // 삭제
    @PostMapping("delete")
    public ResponseEntity<String> deleteSchedule(@ModelAttribute("classList") DeleteClassDTO deleteClassDTO){

        adminService.deleteClass(deleteClassDTO);
        log.info("delete_List={}", deleteClassDTO.getDeleteClassList());
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
