package com.attendance.scheduler.teacher.api;

import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.teacher.application.TeacherCertService;
import com.attendance.scheduler.teacher.application.TeacherService;
import com.attendance.scheduler.teacher.dto.*;
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

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/manage/")
@RequiredArgsConstructor
public class TeacherCertController {

    public final TeacherService teacherService;
    public final TeacherCertService teacherCertService;

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
        Optional<FindIdDTO> idByEmail = teacherCertService.findIdByEmail(findIdDTO);
        log.info("email={}", idByEmail);
        if (idByEmail.isEmpty()) {
            model.addAttribute("account", new FindIdDTO());
            model.addAttribute("errorMessage", "등록된 이메일이 없습니다.");
            return "cert/findId";
        }

        try {
            findIdDTO.setUsername(idByEmail.get().getUsername());
            teacherCertService.sendUserId(findIdDTO);
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

        if(!teacherCertService.emailConfirmation(findPasswordDTO)) {
            model.addAttribute("errorMessage", "등록된 이메일이 없습니다.");
            model.addAttribute("account", new TeacherDTO());
            return "cert/findPwd";
        }
        if(!teacherCertService.idConfirmation(findPasswordDTO)){
            model.addAttribute("errorMessage", "등록된 아이디가 없습니다.");
            model.addAttribute("account", new TeacherDTO());
            return "cert/findPwd";
        }


        try {
            model.addAttribute("username", findPasswordDTO.getUsername());
            model.addAttribute("auth", new CertDTO());
            teacherCertService.setupAuthNum(findPasswordDTO, session);
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
            teacherCertService.initializePassword(pwdEditDTO);
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

        if (teacherService.findTeacherEmailByUsername(emailDTO).isPresent()) {
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        editEmailDTO.setUsername(auth.getName());
        try{
            teacherCertService.updateEmail(editEmailDTO);
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
