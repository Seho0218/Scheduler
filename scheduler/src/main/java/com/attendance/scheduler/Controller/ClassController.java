package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Service.ClassService;
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
@RequestMapping("/search/")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    //  수업 조회 폼
    @GetMapping("")
    public String searchClass(Model model) {
        model.addAttribute("studentClass", new StudentClassDTO());
        return "class/search";
    }

    //   수업 조회
    @PostMapping("findClass")
    public String findClass(@Validated @ModelAttribute("studentClass") StudentClassDTO studentClassDTO,
                            BindingResult bindingResult, Model model) {
        //학생 수업 조회
        StudentClassDTO studentClassesList = classService
                .findStudentClasses(studentClassDTO);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "class/search";
        }

        if (studentClassesList == null) {
            model.addAttribute("nullStudentName", "등록된 이름이 없습니다.");
            return "class/search";
        }

        searchStudentClass(model, studentClassesList);

        return "class/findClass";
    }

    //제출
    @PostMapping("submit")
    public String submitForm(@Validated @ModelAttribute("class") ClassDTO classDTO,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            getClassList(model);
            log.info("errors={}", bindingResult);
            return "index";
        }

        StudentClassDTO studentClassDTO = new StudentClassDTO();
        studentClassDTO.setStudentName(classDTO.getStudentName());
        StudentClassDTO studentClassesList = classService.findStudentClasses(studentClassDTO);

        if (studentClassesList != null) {
            getClassList(model);
            model.addAttribute("studentName", "이미 수업을 신청했습니다.");
            return "index";
        }

        try {
            classService.saveClassTable(classDTO);
            return "redirect:/completion";
        }catch (Exception e){
            getClassList(model);
            log.info("e.getMessage() = {}", e.getMessage());
            return "index";
        }
    }
    //  수업 수정

    @PostMapping("modify")
    public String modifyClass(@Validated @ModelAttribute("class") ClassDTO classDTO,
                              BindingResult bindingResult, Model model) {
        log.info("studentInfo={}", classDTO.getStudentName());

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "class/findClass";
        }

        try{
            classService.saveClassTable(classDTO);
            return "redirect:/completion";
        }catch(Exception e){
            StudentClassDTO studentClassDTO = new StudentClassDTO();
            studentClassDTO.setStudentName(classDTO.getStudentName());
            searchStudentClass(model, classService.findStudentClasses(studentClassDTO));
            model.addAttribute("errorMessage", e.getMessage());

            log.info("studentClass={}", classDTO);
            log.info("error={}", e.getMessage());

            return "class/findClass";
        }
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
/*
* 수업 수정시 할 경우, 학생 이름으로 조회한 결과.
*
* 수업 리스트 정보를 가져온 후, 조회 학생의 시간표를 제외하여 수업 시간표를 출력한 후
* 그 결과에 조회 학생의 정보를 재출력
* */
    private void searchStudentClass(Model model, StudentClassDTO studentClassesList) {

        ClassListDTO classesOrderByAsc = classService.findAllClasses();

        List<Integer> mondayClassList = classesOrderByAsc.getMondayClassList();
        List<Integer> tuesdayClassList = classesOrderByAsc.getTuesdayClassList();
        List<Integer> wednesdayClassList = classesOrderByAsc.getWednesdayClassList();
        List<Integer> thursdayClassList = classesOrderByAsc.getThursdayClassList();
        List<Integer> fridayClassList = classesOrderByAsc.getFridayClassList();


        mondayClassList.remove(studentClassesList.getMonday());
        tuesdayClassList.remove(studentClassesList.getTuesday());
        wednesdayClassList.remove(studentClassesList.getWednesday());
        thursdayClassList.remove(studentClassesList.getThursday());
        fridayClassList.remove(studentClassesList.getFriday());


        model.addAttribute("classInMondayList", mondayClassList);
        model.addAttribute("classInTuesdayList", tuesdayClassList);
        model.addAttribute("classInWednesdayList", wednesdayClassList);
        model.addAttribute("classInThursdayList", thursdayClassList);
        model.addAttribute("classInFridayList", fridayClassList);

        log.info("monday = {}", mondayClassList);
        log.info("tuesday = {}", tuesdayClassList);
        log.info("wednesday = {}", wednesdayClassList);
        log.info("thursday = {}", thursdayClassList);
        log.info("friday = {}", fridayClassList);

        model.addAttribute("studentClass", new StudentClassDTO());
        model.addAttribute("studentClassList", studentClassesList);
    }
}
