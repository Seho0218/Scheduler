package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Service.SearchClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BasicController {

    private final SearchClassService searchClassService;

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

        ClassListDTO classesOrderByAsc = searchClassService.findClassesOrderByAsc();

        model.addAttribute("classInMondayList", classesOrderByAsc.getClassInMondayList());
        model.addAttribute("classInTuesdayList", classesOrderByAsc.getClassInTuesdayList());
        model.addAttribute("classInWednesdayList", classesOrderByAsc.getClassInWednesdayList());
        model.addAttribute("classInThursdayList", classesOrderByAsc.getClassInThursdayList());
        model.addAttribute("classInFridayList", classesOrderByAsc.getClassInFridayList());

        log.info("monday = {}", classesOrderByAsc.getClassInMondayList());
        log.info("tuesday = {}", classesOrderByAsc.getClassInTuesdayList());
        log.info("wednesday = {}", classesOrderByAsc.getClassInWednesdayList());
        log.info("thursday = {}", classesOrderByAsc.getClassInThursdayList());
        log.info("friday = {}", classesOrderByAsc.getClassInFridayList());
    }
}