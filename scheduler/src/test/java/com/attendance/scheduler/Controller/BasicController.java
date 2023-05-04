package com.attendance.scheduler.Controller;

import com.attendance.scheduler.dto.ClassDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class BasicController {

    @GetMapping("/index")
    public String basic(){
       return"/";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute ClassDTO classDTO, RedirectAttributes redirectAttributes){
        return "/";
    }
}
