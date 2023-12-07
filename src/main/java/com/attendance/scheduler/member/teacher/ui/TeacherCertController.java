package com.attendance.scheduler.member.teacher.ui;

import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.infra.email.FindPasswordDTO;
import com.attendance.scheduler.infra.email.HtmlEmailService;
import com.attendance.scheduler.member.teacher.application.TeacherCertService;
import com.attendance.scheduler.member.teacher.dto.*;
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
@RequestMapping("/help/")
@RequiredArgsConstructor
public class TeacherCertController {

    public final TeacherCertService teacherCertService;
    public final HtmlEmailService htmlEmailService;

    /*
     * Find ID Section
     */

    /*
     * send id by Email
     * */
    @PostMapping("sendUserId")
    public String sendEmail(@Validated @ModelAttribute("account") FindIdDTO findIdDTO,
                            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "member/help/findId";
        }

        Optional<FindIdDTO> idByEmail = teacherCertService.findIdByEmail(findIdDTO);
        log.info("email={}", idByEmail);

        if (idByEmail.isEmpty()) {
            model.addAttribute("account", new FindIdDTO());
            model.addAttribute("errorMessage", "등록된 이메일이 없습니다.");
            return "member/help/findId";
        }

        try {
            findIdDTO.setUsername(idByEmail.get().getUsername());
            htmlEmailService.sendUserId(findIdDTO);
        } catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
        }

        return "member/help/idCompletion";
    }

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
            return "member/help/findPwd";
        }

        if(!teacherCertService.emailConfirmation(findPasswordDTO)) {
            model.addAttribute("errorMessage", "등록된 이메일이 없습니다.");
            model.addAttribute("account", new TeacherDTO());
            return "member/help/findPwd";
        }

        if(!teacherCertService.idConfirmation(findPasswordDTO)){
            model.addAttribute("errorMessage", "등록된 아이디가 없습니다.");
            model.addAttribute("account", new TeacherDTO());
            return "member/help/findPwd";
        }

        try {
            model.addAttribute("username", findPasswordDTO.getUsername());
            model.addAttribute("auth", new CertDTO());
            htmlEmailService.sendAuthNum(findPasswordDTO, session);
            return "member/help/authNum";
        } catch (Exception e) {
            model.addAttribute("account", new TeacherDTO());
            model.addAttribute("errorMessage", e.getMessage());
            return "member/help/findPwd";
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
            return "member/help/authNum";
        }

        // 현재시간이 만료시간이 지났다면
        if (LocalDateTime.now().isAfter((LocalDateTime)sessionAuthNumMap.get("endTime"))) {
            model.addAttribute("auth", new CertDTO());
            model.addAttribute("errorMessage","인증시간이 만료되었습니다");
            session.setAttribute(authNum, null);
            session.setMaxInactiveInterval(0);
            return "member/help/authNum";
        }

        // 인증번호
        String sessionAuthNum = (String) sessionAuthNumMap.get(teacherId);
        if (authNum.equals(sessionAuthNum)) {
            // 인증번호가 일치하면
            model.addAttribute("pwdEdit", new PwdEditDTO());
            model.addAttribute("username", certDTO);
            return "member/help/initializePassword";
        } else {
            model.addAttribute("auth", new CertDTO());
            model.addAttribute("username", certDTO);
            model.addAttribute("errorMessage","인증번호가 일치하지 않습니다");
            return "member/help/authNum";
        }
    }

    //로그인 상태에서 변경
    @GetMapping("password")
    public String changePassword(Model model) {
        model.addAttribute("pwdEdit", new PwdEditDTO());
        return "member/help/changePassword";
    }

    @PostMapping("password")
    public String authCompletion(PwdEditDTO pwdEditDTO, Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!auth.getName().equals("anonymousUser")) {
            pwdEditDTO.setUsername(auth.getName());
        }

        try {
            teacherCertService.initializePassword(pwdEditDTO);
            return "redirect:/help/completion";
        }catch (Exception e) {
            log.info("error = {}", e.getMessage());
            model.addAttribute("class", new ClassDTO());
            return "redirect:/";
        }
    }

    //로그인 상태에서
    @GetMapping("email")
    public String changeEmail(Model model){

        FindIdDTO findIdDTO = new FindIdDTO();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        findIdDTO.setUsername(auth.getName());

        if (teacherCertService.findIdByEmail(findIdDTO).isPresent()) {
            // 교사 계정 정보가 있을 경우, emailDTO 정보 추가
            findIdDTO.setEmail(teacherCertService.findIdByEmail(findIdDTO).get().getEmail());
        }

        try {
            model.addAttribute("emailEdit", new EditEmailDTO());
            model.addAttribute("username", findIdDTO);
            return "member/help/changeEmail";
        } catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
            model.addAttribute("class", new ClassDTO());
            return "redirect:/";
        }
    }

    @PostMapping("email")
    public String updateEmail(EditEmailDTO editEmailDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        editEmailDTO.setUsername(auth.getName());

        try {
            teacherCertService.updateEmail(editEmailDTO);
            return "redirect:/help/completion";
        } catch (Exception e) {
            return "manage/class";
        }
    }
}
