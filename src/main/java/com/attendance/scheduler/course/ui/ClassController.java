package com.attendance.scheduler.course.ui;

import com.attendance.scheduler.course.application.ClassService;
import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.course.dto.StudentClassDTO;
import com.attendance.scheduler.member.student.application.StudentService;
import com.attendance.scheduler.member.student.dto.ClassListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/search/")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;
    private final StudentService studentService;

    //   수업 조회
    @PostMapping("findClass")
    public String findClass(@Validated @ModelAttribute("studentClassDTO") StudentClassDTO studentClassDTO,
                            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "index";
        }

        boolean existStudentEntityByStudentName = studentService.existStudentEntityByStudentName(studentClassDTO.getStudentName());

        if(!existStudentEntityByStudentName) {
            model.addAttribute("nullStudentName", "등록 되지 않은 학생입니다.");
            //ToKnow th:text로 연결해야함.
            return "index";
        }

        searchStudentClass(studentClassDTO.getStudentName(), model);
        return "class/findClass";
    }

    //제출
    @PostMapping("submit")
    public String submitForm(@Validated @ModelAttribute("classDTO") ClassDTO classDTO,
                             BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            searchStudentClass(classDTO.getStudentName(), model);
            log.info("errors={}", bindingResult);
            return "class/findClass";
        }

        try {
            classService.saveClassTable(classDTO);
            return "redirect:/completion";
        }catch (Exception e){
            searchStudentClass(classDTO.getStudentName(), model);
            model.addAttribute("error", e.getMessage());
            log.info("e.getMessage() = {}", e.getMessage());
            return "class/findClass";
        }
    }

/*
* 수업 수정시 할 경우, 학생 이름으로 조회한 결과.
*
* 수업 리스트 정보를 가져온 후, 조회 학생의 시간표를 제외하여 수업 시간표를 출력한 후
* 그 결과에 조회 학생의 정보를 재출력
* */
    private void searchStudentClass(String studentName, Model model) {

        Optional<StudentClassDTO> studentClasses = classService.findStudentClasses(studentName);

        ClassListDTO allClasses = classService.findTeachersClasses(studentName);

        List<Integer> mondayClassList = allClasses.getMondayClassList();
        List<Integer> tuesdayClassList = allClasses.getTuesdayClassList();
        List<Integer> wednesdayClassList = allClasses.getWednesdayClassList();
        List<Integer> thursdayClassList = allClasses.getThursdayClassList();
        List<Integer> fridayClassList = allClasses.getFridayClassList();


        model.addAttribute("classDTO", new ClassDTO());
        model.addAttribute("studentName", studentName);
        model.addAttribute("classInMondayList", mondayClassList);
        model.addAttribute("classInTuesdayList", tuesdayClassList);
        model.addAttribute("classInWednesdayList", wednesdayClassList);
        model.addAttribute("classInThursdayList", thursdayClassList);
        model.addAttribute("classInFridayList", fridayClassList);
        model.addAttribute("studentClassList", new StudentClassDTO());

        if(studentClasses.isPresent()) {
            mondayClassList.remove(studentClasses.get().getMonday());
            tuesdayClassList.remove(studentClasses.get().getTuesday());
            wednesdayClassList.remove(studentClasses.get().getWednesday());
            thursdayClassList.remove(studentClasses.get().getThursday());
            fridayClassList.remove(studentClasses.get().getFriday());
            model.addAttribute("studentClassList", studentClasses.get());
        }
    }
}
