package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Service.SearchNotEmptyClassService;
import com.attendance.scheduler.Service.SubmitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/*")
@RequiredArgsConstructor
public class BasicController {

    public final SubmitService submitService;
    public final SearchNotEmptyClassService searchNotEmptyClassService;

    @GetMapping("/")
    public String basic(Model model){

        model.addAttribute("ClassInMondayList",
                searchNotEmptyClassService.findByMondayClassesOrderByAsc());
        log.info("Monday = {}", searchNotEmptyClassService.findByMondayClassesOrderByAsc());

        model.addAttribute("ClassInTuesdayList",
                searchNotEmptyClassService.findByTuesdayClassesOrderByAsc());
        log.info("Tuesday = {}", searchNotEmptyClassService.findByTuesdayClassesOrderByAsc());


        model.addAttribute("ClassInWednesdayList",
                searchNotEmptyClassService.findByWednesdayClassesOrderByAsc());
        log.info("Wednesday = {}", searchNotEmptyClassService.findByWednesdayClassesOrderByAsc());


        model.addAttribute("ClassInThursdayList",
                searchNotEmptyClassService.findByThursdayClassesOrderByAsc());
        log.info("Thursday = {}", searchNotEmptyClassService.findByThursdayClassesOrderByAsc());


        model.addAttribute("ClassInFridayList",
                searchNotEmptyClassService.findByFridayClassesOrderByAsc());
        log.info("Friday = {}", searchNotEmptyClassService.findByFridayClassesOrderByAsc());

        return "index";
    }

    @PostMapping("submit")
    public String submitForm(@ModelAttribute ClassDTO classDTO,
                             BindingResult bindingResult){

        try {
            submitService.saveClassTable(classDTO);
            return "completion";
        }catch(Exception e){
            e.printStackTrace();
            return "/";
        }
    }

    @GetMapping("admin")
    public String adminPage(){
        return "/admin/manage";
    }
}
