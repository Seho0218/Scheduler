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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/*")
@RequiredArgsConstructor
public class BasicController {
    private final SearchNotEmptyClassService searchNotEmptyClassRepository;

    private final SubmitService submitService;
    private final AdminService adminService;

    @GetMapping("/")
    public String basic(Model model){

        List<Object[]> classesOrderByAsc = searchNotEmptyClassRepository.findClassesOrderByAsc();

        List<Integer> monday = new ArrayList<>();
        List<Integer> tuesday = new ArrayList<>();
        List<Integer> wednesday = new ArrayList<>();
        List<Integer> thursday = new ArrayList<>();
        List<Integer> friday = new ArrayList<>();

        findClassDays(model, classesOrderByAsc, monday, tuesday, wednesday, thursday, friday);

        model.addAttribute("class", new ClassDTO());

        return "index";
    }

    @PostMapping("submit")
    public String submitForm(@Validated @ModelAttribute("class") ClassDTO classDTO,
                             BindingResult bindingResult, Model model){

        List<Object[]> classesOrderByAsc = searchNotEmptyClassRepository.findClassesOrderByAsc();

        List<Integer> monday = new ArrayList<>();
        List<Integer> tuesday = new ArrayList<>();
        List<Integer> wednesday = new ArrayList<>();
        List<Integer> thursday = new ArrayList<>();
        List<Integer> friday = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);

            findClassDays(model, classesOrderByAsc, monday, tuesday, wednesday, thursday, friday);

            return "index";
        }

        submitService.saveClassTable(classDTO);

        return "completion";
    }

    private static void findClassDays(Model model, List<Object[]> classesOrderByAsc, List<Integer> monday, List<Integer> tuesday, List<Integer> wednesday, List<Integer> thursday, List<Integer> friday) {
        for (Object[] row : classesOrderByAsc) {

            Integer mondayValue = (Integer) row[0];
            monday.add(mondayValue);


            Integer tuesdayValue = (Integer) row[1];
            tuesday.add(tuesdayValue);


            Integer wednesdayValue = (Integer) row[2];
            wednesday.add(wednesdayValue);


            Integer thursdayValue = (Integer) row[3];
            thursday.add(thursdayValue);


            Integer fridayValue = (Integer) row[4];
            friday.add(fridayValue);

        }

        model.addAttribute("ClassInMondayList", monday);
        log.info("monday = {}", monday);
        model.addAttribute("ClassInTuesdayList", tuesday);
        log.info("tuesday = {}", tuesday);
        model.addAttribute("ClassInWednesdayList", wednesday);
        log.info("wednesday = {}", wednesday);
        model.addAttribute("ClassInThursdayList", thursday);
        log.info("thursday = {}", thursday);
        model.addAttribute("ClassInFridayList", friday);
        log.info("friday = {}", friday);
    }

    @GetMapping("admin")
    public String adminPage(Model model){

        List<ClassDTO> classTable = adminService.findClassTable();

        model.addAttribute("findClassTable", classTable);
        log.info("student = {} ", adminService.findClassTable());

        return "/admin/manage";
    }
}
