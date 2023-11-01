package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.EditEmailDTO;
import com.attendance.scheduler.Dto.EmailDTO;
import com.attendance.scheduler.Dto.Teacher.*;
import com.attendance.scheduler.Service.AdminService;
import com.attendance.scheduler.Service.CertService;
import com.attendance.scheduler.Service.TeacherService;
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
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/cert/")
@RequiredArgsConstructor
public class CertController {

    private final AdminService adminService;
    private final TeacherService teacherService;
    private final CertService certService;

    /*
* Find ID Section
*/
    /*
    * findId Form
    * */

    @GetMapping("findId")
    public String findId(Model model) {
        model.addAttribute("account", new TeacherDTO());
        return "cert/findId";
    }
    /*
    * send id by Email
    * */

    @PostMapping("sendUserId")
    public String sendEmail(@Validated @ModelAttribute("account") FindIdDTO findIdDTO,
                                            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "cert/findId";
        }
        Optional<FindIdDTO> idByEmail = certService.findIdByEmail(findIdDTO);
        log.info("email={}", idByEmail);
        if (idByEmail.isEmpty()) {
            model.addAttribute("account", new FindIdDTO());
            model.addAttribute("errorMessage", "등록된 이메일이 없습니다.");
            return "cert/findId";
        }

        try {
            findIdDTO.setUsername(idByEmail.get().getUsername());
            certService.sendUserId(findIdDTO);
        } catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
        }

        return "cert/idCompletion";
    }

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

        if(!certService.emailConfirmation(findPasswordDTO)) {
            model.addAttribute("errorMessage", "등록된 이메일이 없습니다.");
            model.addAttribute("account", new TeacherDTO());
            return "cert/findPwd";
        }
        if(!certService.idConfirmation(findPasswordDTO)){
            model.addAttribute("errorMessage", "등록된 아이디가 없습니다.");
            model.addAttribute("account", new TeacherDTO());
            return "cert/findPwd";
        }


        try {
            model.addAttribute("username", findPasswordDTO.getUsername());
            model.addAttribute("auth", new CertDTO());
            certService.setupAuthNum(findPasswordDTO, session);
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
    public String changePassword(Model model, PwdEditDTO pwdEditDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        pwdEditDTO.setUsername(auth.getName());
        try {
            model.addAttribute("pwdEdit", new PwdEditDTO());
            model.addAttribute("username", pwdEditDTO);
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
        try {
            certService.PwdEdit(pwdEditDTO);
            return "redirect:/cert/completion";
        }catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
            model.addAttribute("class", new ClassDTO());
            return "redirect:/";
        }
    }
    //TODO

    @GetMapping("changeEmail")
    public String changeEmail(Model model){

        EmailDTO emailDTO = new EmailDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        emailDTO.setUsername(auth.getName());

        if (adminService.findAdminEmailByID(emailDTO).isPresent()) {
            // 관리자 계정 정보가 있을 경우, emailDTO 정보 추가
            emailDTO.setEmail(adminService.findAdminEmailByID(emailDTO).get().getEmail());
        } else if (teacherService.findTeacherEmailByUsername(emailDTO).isPresent()) {
            // 교사 계정 정보가 있을 경우, emailDTO 정보 추가
            emailDTO.setEmail(teacherService.findTeacherEmailByUsername(emailDTO).get().getEmail());
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