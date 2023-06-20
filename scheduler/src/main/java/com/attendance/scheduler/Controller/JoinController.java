package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Service.JoinService;
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
@RequestMapping("/join/*")
@RequiredArgsConstructor
public class JoinController {

    private static final String duplicateErrorMessage = "중복된 아이디 입니다.";
    private final JoinService joinService;

    //회원가입 폼
    @GetMapping("teacher")
    public String joinForm(Model model) {
        model.addAttribute("join", new JoinTeacherDTO());
        return "join";
    }

    //회원가입 완료
    @PostMapping("approved")
    public String approved(@Validated @ModelAttribute("join") JoinTeacherDTO joinTeacherDTO, BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "join";
        }

        JoinTeacherDTO duplicateTeacherId = joinService.findDuplicateTeacherId(joinTeacherDTO);

        if (duplicateTeacherId != null) {
            log.info("teacherId={}", duplicateTeacherId.getTeacherId());
            model.addAttribute("errorMessage", duplicateErrorMessage);
            return "join";
        }

        try {
            joinService.joinTeacher(joinTeacherDTO);
            model.addAttribute("login", new JoinTeacherDTO());
            return "redirect:login";
        } catch (Exception e) {
            e.printStackTrace();
            return "join";
        }
    }
}
