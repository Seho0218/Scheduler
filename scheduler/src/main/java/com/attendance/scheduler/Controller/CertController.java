package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Teacher.*;
import com.attendance.scheduler.Service.CertService;
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

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/cert/")
@RequiredArgsConstructor
public class CertController {

    private final CertService certService;

    private static final String emailErrorMessage = "등록된 이메일이 없습니다.";
    private static final String idErrorMessage = "등록된 아이디가 없습니다.";

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
        String idByEmail = certService.findIdByEmail(findIdDTO);
        log.info("email={}", idByEmail);

        if (idByEmail == null) {
            model.addAttribute("account", new FindIdDTO());
            model.addAttribute("errorMessage", emailErrorMessage);
            return "cert/findId";
        }

        try {
            findIdDTO.setId(idByEmail);
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
    * Validate ID and Email
    **/
    @PostMapping("findPwd")
    public String idEmailConfirm(@Validated @ModelAttribute("account") FindPasswordDTO findPasswordDTO,
                       BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "cert/findPwd";
        }

        if(!certService.emailConfirmation(findPasswordDTO)) {
            model.addAttribute("errorMessage", emailErrorMessage);
            model.addAttribute("account", new TeacherDTO());
            return "cert/FindPwd";
        }
        if(!certService.idConfirmation(findPasswordDTO)){
            model.addAttribute("errorMessage", idErrorMessage);
            model.addAttribute("account", new TeacherDTO());
            return "cert/FindPwd";
        }
        return "cert/authNumView";
//        return "redirect:/cert/authNumView";
    }

    /*
     * Set and Send teacher AuthNum
     * */
    @PostMapping("authNum")
    private String authNum(FindPasswordDTO findPasswordDTO, Model model, HttpSession session){
        log.info("findPasswordDTO={}", findPasswordDTO);
        try {
            model.addAttribute("auth", new CertDTO());
            model.addAttribute("teacherId", findPasswordDTO);
            certService.setupAuthNum(findPasswordDTO, session);
            return "cert/authNumView";
        } catch (Exception e) {
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

        Map<String, Object> sessionAuthNumMap = (Map<String, Object>) session.getAttribute(certDTO.getTeacherId());
        String teacherId = certDTO.getTeacherId();
        String authNum = certDTO.getAuthNum();

        if (sessionAuthNumMap.isEmpty()) {
            model.addAttribute("auth", new CertDTO());
            model.addAttribute("teacherId", certDTO);
            model.addAttribute("errorMessage","인증번호를 전송해주세요");
            return "cert/authNumView";
        }


        // 현재시간이 만료시간이 지났다면
        if (LocalDateTime.now().isAfter((LocalDateTime)sessionAuthNumMap.get("endTime"))) {
            model.addAttribute("auth", new CertDTO());
            model.addAttribute("errorMessage","인증시간이 만료되었습니다");
            session.setAttribute(authNum, null);
            session.setMaxInactiveInterval(0);
            return "cert/authNumView";
        }

        // 인증번호
        String sessionAuthNum = (String) sessionAuthNumMap.get(teacherId);
        if (!authNum.equals(sessionAuthNum)) {
            model.addAttribute("auth", new CertDTO());
            model.addAttribute("teacherId", certDTO);
            model.addAttribute("errorMessage","인증번호가 일치하지 않습니다");
            return "cert/authNumView";
        } else {
            // 인증번호가 일치하면
            model.addAttribute("pwdEdit", new PwdEditDTO());
            model.addAttribute("teacherId", certDTO);
            return "cert/updatePassword";
        }
    }
    // 인증 완료 후
    @PostMapping("updatePassword")
    public String authCompletion(Model model, PwdEditDTO pwdEditDTO) {
        model.addAttribute("teacherId", pwdEditDTO);
        try {
            certService.PwdEdit(pwdEditDTO);
            return "cert/pwdCompletion";
        }catch (Exception e) {
            log.info("send Id error = {}", e.getMessage());
            return "index";
        }
    }
}
