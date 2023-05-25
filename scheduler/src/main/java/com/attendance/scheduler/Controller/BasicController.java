package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Service.SearchNotEmptyClassService;
import com.attendance.scheduler.Service.SubmitService;
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

//  제출
    @PostMapping("submit")
    public String SubmitForm(@Validated @ModelAttribute("class") ClassDTO classDTO,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            GetClassList(model);
            log.info("errors={}", bindingResult);
            return "index";
        }

        submitService.saveClassTable(classDTO);
        return "redirect:/completion";
    }
//  제출 완료 폼
    @GetMapping("completion")
    public String SubmitForm() {
        return "/completion";
    }



//  수업 조회 폼
    @GetMapping("search")
    public String SearchClass(Model model) {
        model.addAttribute("class", new StudentClassDTO());
        return "search";
    }

//   수업 조회
    @PostMapping("findClass")
    public String findClass(@Validated @ModelAttribute("class") StudentClassDTO studentClassDTO,
                            BindingResult bindingResult, Model model) {

        StudentClassDTO studentClasses = searchNotEmptyClassService.findStudentClasses(studentClassDTO);

        if (bindingResult.hasErrors() || studentClasses == null) {
            log.info("errors={}", bindingResult);
            return "search";
        }

        log.info("studentClasses={}", studentClasses);

        model.addAttribute("class", new StudentClassDTO());
        model.addAttribute("classList", studentClasses);
        return "findClass";
    }

//  수업 수정
    @PostMapping("modify")
    public String modifyClass(@Validated @ModelAttribute("class") ClassDTO classDTO,
                              BindingResult bindingResult) {


        log.info("studentInfo={}", classDTO.getStudentName());
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "findClass";
        }
        submitService.saveClassTable(classDTO);
        return "completion";
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
