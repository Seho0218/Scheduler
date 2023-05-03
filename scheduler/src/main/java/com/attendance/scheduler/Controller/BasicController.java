package com.attendance.scheduler.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class BasicController {

    @GetMapping("/index")
    public ModelAndView basic(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/");
        return mav;
    }
}
