package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Service.SubmitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/*")
@RequiredArgsConstructor
public class BasicController {

    public final SubmitService submitService;

    @GetMapping("/")
    public String basic(){
       return "/index";
    }

    @PostMapping("submit")
    public String submitForm(@ModelAttribute ClassDTO classDTO,
                             BindingResult bindingResult){

        try {
            submitService.saveClassTable(classDTO);
            return "/completion";
        }catch(Exception e){
            e.printStackTrace();
            return "/";
        }
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "/admin/manage";
    }
}
