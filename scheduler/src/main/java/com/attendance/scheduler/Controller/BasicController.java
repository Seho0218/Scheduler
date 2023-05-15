package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Service.AdminService;
import com.attendance.scheduler.Service.SearchNotEmptyClassService;
import com.attendance.scheduler.Service.SubmitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/*")
@RequiredArgsConstructor
public class BasicController {

    private final SubmitService submitService;
    private final SearchNotEmptyClassService searchNotEmptyClassService;
    private final AdminService adminService;

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


        model.addAttribute("class", new ClassDTO());

        return "index";
    }

    @PostMapping("submit")
    public String submitForm(@Validated @ModelAttribute("class") ClassDTO classDTO,
                             BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
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

        submitService.saveClassTable(classDTO);

        return "completion";
    }

    @GetMapping("admin")
    public String adminPage(Model model){

        List<ClassDTO> classTable = adminService.findClassTable();

        model.addAttribute("findClassTable", classTable);
        log.info("student = {} ", adminService.findClassTable());

        return "/admin/manage";
    }
}
