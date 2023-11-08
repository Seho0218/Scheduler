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

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminCertController {

    public final AdminService adminService;

    /*
     * Find Password Section
     */


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
            adminService.setupAuthNum(findPasswordDTO, session);
            model.addAttribute("auth", new CertDTO());
            return "cert/authNum";
        } catch (Exception e) {
            model.addAttribute("account", new TeacherDTO());
            model.addAttribute("errorMessage", e.getMessage());
            return "cert/findPwd";
        }
    }

    /*
     * AuthNum Check
     * */
    @PostMapping("authNumCheck")
    private String authNumCheck(Model model, CertDTO certDTO, HttpSession session) {
        log.info("CertDTO={}", certDTO);

        Map<String, Object> sessionAuthNumMap = (Map<String, Object>) session.getAttribute(certDTO.getUsername());
        String teacherId = certDTO.getUsername();
        String authNum = certDTO.getAuthNum();

        if (sessionAuthNumMap.isEmpty()) {
            model.addAttribute("auth", new CertDTO());
            model.addAttribute("username", certDTO);
            model.addAttribute("errorMessage","인증번호를 전송해주세요");
            return "cert/authNum";
        }


        // 현재시간이 만료시간이 지났다면
        if (LocalDateTime.now().isAfter((LocalDateTime)sessionAuthNumMap.get("endTime"))) {
            model.addAttribute("auth", new CertDTO());
            model.addAttribute("errorMessage","인증시간이 만료되었습니다");
            session.setAttribute(authNum, null);
            session.setMaxInactiveInterval(0);
            return "cert/authNum";
        }

        // 인증번호
        String sessionAuthNum = (String) sessionAuthNumMap.get(teacherId);
        if (authNum.equals(sessionAuthNum)) {
            // 인증번호가 일치하면
            model.addAttribute("pwdEdit", new PwdEditDTO());
            model.addAttribute("username", certDTO);
            return "cert/changePassword";
        } else {
            model.addAttribute("auth", new CertDTO());
            model.addAttribute("username", certDTO);
            model.addAttribute("errorMessage","인증번호가 일치하지 않습니다");
            return "cert/authNum";
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
}
