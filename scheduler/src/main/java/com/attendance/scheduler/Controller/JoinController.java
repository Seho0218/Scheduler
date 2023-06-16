package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Teacher.JoinTeacherDTO;
import com.attendance.scheduler.Service.TeacherService;
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
    private final TeacherService teacherService;

    @GetMapping("teacher")
    public String joinForm(Model model) {
        model.addAttribute("join", new JoinTeacherDTO());
        return "join";
    }

    @PostMapping("approved")
    public String approved(@Validated @ModelAttribute("join") JoinTeacherDTO joinTeacherDTO, BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "join";
        }

        JoinTeacherDTO duplicateTeacherId = teacherService.findDuplicateTeacherId(joinTeacherDTO);

        if (duplicateTeacherId != null) {
            log.info("teacherId={}", duplicateTeacherId.getTeacherId());
            model.addAttribute("errorMessage", duplicateErrorMessage);
            return "join";
        }

        try {
            teacherService.joinTeacher(joinTeacherDTO);
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
            return "join";
        }
    }
}
