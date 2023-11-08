package com.attendance.scheduler.common;

import com.attendance.scheduler.teacher.dto.TeacherDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cert/")
public class CertController {

    /*
     * findId Form
     * */
    @GetMapping("findId")
    public String findId(Model model) {
        model.addAttribute("account", new TeacherDTO());
        return "cert/findId";
    }

    @GetMapping("findPassword")
    public String findPassword(Model model) {
        model.addAttribute("account", new TeacherDTO());
        return "cert/findPwd";
    }

    @GetMapping("completion")
    public String updateCompletionForm() {
        return "cert/updateCompletion";
    }
}
