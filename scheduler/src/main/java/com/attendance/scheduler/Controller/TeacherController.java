package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.StudentInformationDTO;
import com.attendance.scheduler.Dto.Teacher.DeleteClassDTO;
import com.attendance.scheduler.Service.ClassService;
import com.attendance.scheduler.Service.ManageStudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/manage/")
@RequiredArgsConstructor
public class TeacherController {

    // 관리자 로직
    private final ManageStudentService manageStudentService;

    //수업 조회
    private final ClassService classService;

    // 조회
    @GetMapping("class")
    public String managePage(Model model) {
        List<ClassDTO> classTable = classService.findClassByStudent();
        model.addAttribute("classList", new DeleteClassDTO()); //있어야 빠름
        model.addAttribute("studentList", classTable);
        log.info("student = {}", classService.findClassByStudent());
        return "manage/class";
    }

    // 삭제
    @PostMapping("delete")
    public ResponseEntity<String> deleteSchedule(@ModelAttribute("classList") DeleteClassDTO deleteClassDTO){
        classService.deleteClass(deleteClassDTO);
        log.info("delete_List={}", deleteClassDTO.getDeleteClassList());
        return ResponseEntity.ok("삭제되었습니다.");
    }

    //학생 리스트
    @GetMapping("studentList")
    public String studentList(StudentInformationDTO studentInformationDTO, Model model) {
        List<StudentInformationDTO> studentInformationList = manageStudentService
                .findStudentInformationList(studentInformationDTO);
        model.addAttribute("studentObject", new StudentInformationDTO());
        model.addAttribute("studentList", studentInformationList);
        return "manage/studentList";
    }

    //학생 정보 등록
    @GetMapping("registerStudentInformation")
    public String addStudentInformation(Model model) {
        model.addAttribute("studentObject", new StudentInformationDTO());
        return "manage/registerStudentInformation";
    }

    @PostMapping("saveStudentList")
    public String saveStudentList(@Validated @ModelAttribute("studentObject") StudentInformationDTO studentInformationDTO, Model model) {
        try {
            manageStudentService.registerStudentInformation(studentInformationDTO);
            return "redirect:/manage/studentList";
        } catch (Exception e) {
            System.out.println("e = " + e);
            model.addAttribute("studentObject", new StudentInformationDTO());
            return "manage/registerStudentInformation";
        }
    }

    @PostMapping("deleteStudentList")
    public ResponseEntity<String> deleteStudentList(@Validated @ModelAttribute StudentInformationDTO studentInformationDTO) {
        manageStudentService.deleteStudentInformation(studentInformationDTO);
        log.info("delete List={}", studentInformationDTO);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
