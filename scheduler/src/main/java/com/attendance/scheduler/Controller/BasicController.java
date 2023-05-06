package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Service.SubmitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
@Slf4j
public class BasicController {

    public final SubmitService submitService;

    @GetMapping("/index")
    public String basic(){
       return"/";
    }

    @GetMapping("/submit")
    public String submitForm(@ModelAttribute ClassDTO classDTO, BindingResult bindingResult){

        submitService.saveClassTable(classDTO);

        if (bindingResult.hasErrors()) {
            return "/";
        }
        return "/index.html";
    }
}
