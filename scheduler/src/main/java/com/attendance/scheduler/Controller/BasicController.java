package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Service.ClassService;
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
        getClassList(model);
        model.addAttribute("class", new ClassDTO());
        return "index";
    }

//  제출 완료 폼
    @GetMapping("completion")
    public String completeForm() {
        return "class/completion";
    }

    private void getClassList(Model model) {
        ClassListDTO classesOrderByAsc = classService.findAllClasses();

        model.addAttribute("classList", classesOrderByAsc);

        log.info("monday = {}", classesOrderByAsc.getMondayClassList());
        log.info("tuesday = {}", classesOrderByAsc.getTuesdayClassList());
        log.info("wednesday = {}", classesOrderByAsc.getWednesdayClassList());
        log.info("thursday = {}", classesOrderByAsc.getThursdayClassList());
        log.info("friday = {}", classesOrderByAsc.getFridayClassList());
    }
}