package com.attendance.scheduler.member.admin.controller;

import com.attendance.scheduler.member.admin.application.AdminService;
import com.attendance.scheduler.member.admin.dto.EditEmailDTO;
import com.attendance.scheduler.member.admin.dto.EmailDTO;
import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.infra.email.HtmlEmailService;
import com.attendance.scheduler.member.teacher.dto.PwdEditDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/help/")
public class AdminCertController {

    public final AdminService adminService;
    public final HtmlEmailService htmlEmailService;

    /*
     * Find Password Section
     */
    @GetMapping("password")
    public String changePassword(Model model){
        try {
            model.addAttribute("pwdEdit", new PwdEditDTO());
            return "admin/help/changePassword";
        } catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
            model.addAttribute("class", new ClassDTO());
            return "redirect:/";
        }
    }

    // 인증 완료 후
    @PostMapping("password")
    public String authCompletion(@Valid PwdEditDTO pwdEditDTO, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        pwdEditDTO.setUsername(auth.getName());
        try {
            adminService.initializePassword(pwdEditDTO);
            return "redirect:/help/completion";
        }catch (Exception e) {
            model.addAttribute("class", new ClassDTO());
            return "redirect:/";
        }
    }

    @GetMapping("email")
    public String changeEmail(Model model){

        EmailDTO emailDTO = new EmailDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        emailDTO.setUsername(auth.getName());

        if (adminService.findAdminEmailByID(emailDTO).isPresent()) {
            // 관리자 계정 정보가 있을 경우, emailDTO 정보 추가
            emailDTO.setEmail(adminService.findAdminEmailByID(emailDTO).get().getEmail());
        }

        try {
            model.addAttribute("emailEdit", new EditEmailDTO());
            model.addAttribute("username", emailDTO);
            return "admin/help/changeEmail";
        } catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
            model.addAttribute("class", new ClassDTO());
            return "redirect:/";
        }
    }

    @PostMapping("email")
    public String updateEmail(EditEmailDTO editEmailDTO) {
        try{
            adminService.updateEmail(editEmailDTO);
            return "redirect:cert/completion";
        }catch (Exception e){
            return "manage/class";
        }
    }
}
