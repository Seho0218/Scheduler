package com.attendance.scheduler.admin.api;

import com.attendance.scheduler.admin.application.AdminService;
import com.attendance.scheduler.admin.dto.CertDTO;
import com.attendance.scheduler.admin.dto.EditEmailDTO;
import com.attendance.scheduler.admin.dto.EmailDTO;
import com.attendance.scheduler.admin.dto.FindPasswordDTO;
import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.teacher.dto.PwdEditDTO;
import com.attendance.scheduler.teacher.dto.TeacherDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminCertController {

    public final AdminService adminService;

    /*
     * Find Password Section
     */

    /**
     * FindPassword Form
     */
    @GetMapping("findPassword")
    public String findPassword(Model model) {
        model.addAttribute("account", new TeacherDTO());
        return "cert/findPwd";
    }

    /*
     * Validate ID and Email and Send teacher AuthNum
     **/
    @PostMapping("findPwd")
    public String idEmailConfirm(@Validated @ModelAttribute("account") FindPasswordDTO findPasswordDTO,
                                 BindingResult bindingResult, HttpSession session, Model model) {

        if (bindingResult.hasErrors()) {
            return "cert/findPwd";
        }

        if(!adminService.emailConfirmation(findPasswordDTO)) {
            model.addAttribute("errorMessage", "등록된 이메일이 없습니다.");
            model.addAttribute("account", new TeacherDTO());
            return "cert/findPwd";
        }

        try {
            model.addAttribute("username", findPasswordDTO.getUsername());
            model.addAttribute("auth", new CertDTO());
            adminService.setupAuthNum(findPasswordDTO, session);
            return "cert/authNum";
        } catch (Exception e) {
            model.addAttribute("account", new TeacherDTO());
            model.addAttribute("errorMessage", e.getMessage());
            return "cert/findPwd";
        }
    }

    @GetMapping("changePassword")
    public String changePassword(Model model){
        try {
            model.addAttribute("pwdEdit", new PwdEditDTO());
            return "cert/changePassword";
        } catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
            model.addAttribute("class", new ClassDTO());
            return "redirect:/";
        }
    }

    // 인증 완료 후
    @PostMapping("updatePassword")
    public String authCompletion(PwdEditDTO pwdEditDTO, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        pwdEditDTO.setUsername(auth.getName());
        try {
            adminService.initializePassword(pwdEditDTO);
            return "redirect:/cert/completion";
        }catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
            model.addAttribute("class", new ClassDTO());
            return "redirect:/";
        }
    }

    @GetMapping("changeEmail")
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
            return "cert/changeEmail";
        } catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
            model.addAttribute("class", new ClassDTO());
            return "redirect:/";
        }
    }

    @PostMapping("updateEmail")
    public String updateEmail(EditEmailDTO editEmailDTO) {
        try{
            adminService.updateEmail(editEmailDTO);
            return "redirect:cert/completion";
        }catch (Exception e){
            return "manage/class";
        }
    }

    @GetMapping("completion")
    public String updateCompletionForm() {
        return "cert/updateCompletion";
    }
}
