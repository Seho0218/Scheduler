package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.AdminDTO;
import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Service.SearchNotEmptyClassService;
import com.attendance.scheduler.Service.SubmitService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class BasicController {

    private final SubmitService submitService;
    private final SearchNotEmptyClassService searchNotEmptyClassService;

    @GetMapping("/")
    public String basic(Model model){

        getClassList(model);
        model.addAttribute("class", new ClassDTO());
        return "index";
    }

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("login", new AdminDTO());
        return "login";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    //  수업 조회 폼
    @GetMapping("search")
    public String searchClass(Model model) {
        model.addAttribute("studentClass", new StudentClassDTO());
        return "search";
    }


    //  제출
    @PostMapping("submit")
    public String submitForm(@Validated @ModelAttribute("class") ClassDTO classDTO,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            getClassList(model);
            log.info("errors={}", bindingResult);
            return "index";
        }

        submitService.saveClassTable(classDTO);
        return "completion";
    }

//  제출 완료 폼
    @GetMapping("completion")
    public String completeForm() {
        return "completion";
    }

    private void getClassList(Model model) {

        ClassListDTO classesOrderByAsc = searchNotEmptyClassService.findClassesOrderByAsc();

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