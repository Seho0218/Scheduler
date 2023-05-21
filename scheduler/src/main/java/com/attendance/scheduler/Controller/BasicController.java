package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
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
@RequestMapping("/")
@RequiredArgsConstructor
public class BasicController {

    private final SubmitService submitService;
    private final SearchNotEmptyClassService searchNotEmptyClassService;

    @GetMapping("/")
    public String basic(Model model){

        GetClassList(model);
        model.addAttribute("class", new ClassDTO());
        return "index";
    }

    @PostMapping("submit")
    public String submitForm(@Validated @ModelAttribute("class") ClassDTO classDTO,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            GetClassList(model);
            log.info("errors={}", bindingResult);
            return "index";
        }

        submitService.saveClassTable(classDTO);
        return "redirect:/completion";
    }

    @GetMapping("completion")
    public String submitForm() {
        return "/completion";
    }



    private void GetClassList(Model model) {

        ClassListDTO classesOrderByAsc = searchNotEmptyClassService.findClassesOrderByAsc();

        List<Integer> mondayList = classesOrderByAsc.getClassInMondayList();
        List<Integer> tuesdayList = classesOrderByAsc.getClassInTuesdayList();
        List<Integer> wednesdayList = classesOrderByAsc.getClassInWednesdayList();
        List<Integer> thursdayList = classesOrderByAsc.getClassInThursdayList();
        List<Integer> fridayList = classesOrderByAsc.getClassInFridayList();

        model.addAttribute("ClassInMondayList", mondayList);
        log.info("monday = {}", mondayList);
        model.addAttribute("ClassInTuesdayList", tuesdayList);
        log.info("tuesday = {}", tuesdayList);
        model.addAttribute("ClassInWednesdayList", wednesdayList);
        log.info("wednesday = {}", wednesdayList);
        model.addAttribute("ClassInThursdayList", thursdayList);
        log.info("thursday = {}", thursdayList);
        model.addAttribute("ClassInFridayList", fridayList);
        log.info("friday = {}", fridayList);
    }
}
