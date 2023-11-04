package com.attendance.scheduler.Student;

import com.attendance.scheduler.Course.ClassService;
import com.attendance.scheduler.Course.Dto.ClassDTO;
import com.attendance.scheduler.Student.Dto.ClassListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BasicController {

    private final ClassService classService;

    @GetMapping("/")
    public String basic(Model model){
        ClassListDTO allClasses = classService.findAllClasses();

        model.addAttribute("classList", allClasses);
        model.addAttribute("class", new ClassDTO());
        log.info("allClasses = {}", allClasses);
        return "index";
    }

//  제출 완료 폼
    @GetMapping("completion")
    public String completeForm() {
        return "class/completion";
    }
}