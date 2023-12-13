package com.attendance.scheduler.teacher.controller;

import com.attendance.scheduler.admin.application.AdminService;
import com.attendance.scheduler.course.application.ClassService;
import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.student.application.StudentService;
import com.attendance.scheduler.student.dto.StudentInformationDTO;
import com.attendance.scheduler.teacher.application.TeacherService;
import com.attendance.scheduler.teacher.dto.DeleteClassDTO;
import com.attendance.scheduler.teacher.dto.RegisterStudentDTO;
import com.attendance.scheduler.teacher.dto.StudentSearchCondition;
import com.attendance.scheduler.teacher.dto.TeacherDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequestMapping("/manage/")
@RequiredArgsConstructor
public class TeacherController {

    private final StudentService studentService;
    private final TeacherService teacherService;

    //수업 조회
    private final ClassService classService;
    private final AdminService adminService;

    // 조회
    @GetMapping("class")
    public String managePage(Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<ClassDTO> classTable = classService.findStudentClassList();

        if(auth.getAuthorities().toString().equals("[ROLE_TEACHER]")){
            List<TeacherDTO> teacherInformation = adminService.findTeacherInformation(auth.getName());
            Stream<ClassDTO> stream = classTable
                    .stream().filter(h -> h.getTeacherName()
                            .equals(teacherInformation.get(0).getTeacherName()));
            model.addAttribute("findClassTable", stream);
            return "manage/class";
        }

        model.addAttribute("classList", new DeleteClassDTO()); //있어야 빠름
        model.addAttribute("findClassTable", classTable);
        return "manage/class";
    }

    // 삭제
    @PostMapping("delete")
    public ResponseEntity<String> deleteSchedule(@ModelAttribute("classList") DeleteClassDTO deleteClassDTO){
        classService.deleteClass(deleteClassDTO);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    //학생 리스트
    @GetMapping("studentList")
    public String studentList(StudentSearchCondition studentSearchCondition, Pageable pageable, Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Page<StudentInformationDTO> studentInformationList = teacherService
                .findStudentInformationList(studentSearchCondition, pageable);
        model.addAttribute("maxPage", 5);

        if(auth.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            List<TeacherDTO> teacherList = adminService.getTeacherList();
            model.addAttribute("teacherList", teacherList);
            model.addAttribute("studentList", studentInformationList);
            return "manage/studentList";
        }

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
    public String saveStudentList(@Validated @ModelAttribute("studentObject") RegisterStudentDTO registerStudentDTO, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "manage/registerStudentInformation";
        }

        boolean studentEntityByStudentName = studentService.existStudentEntityByStudentName(registerStudentDTO.getStudentName());

        if (studentEntityByStudentName) {
            model.addAttribute("studentInformation", "이미 등록된 학생의 이름입니다.");
            return "manage/registerStudentInformation";
        }

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        registerStudentDTO.setTeacherEntity(auth.getName());

        try {
            teacherService.registerStudentInformation(registerStudentDTO);
            return "redirect:/manage/studentList";
        } catch (Exception e) {
            model.addAttribute("studentObject", new StudentInformationDTO());
            return "manage/registerStudentInformation";
        }
    }

    @PostMapping("deleteStudentList")
    public ResponseEntity<String> deleteStudentList(@Validated @ModelAttribute StudentInformationDTO studentInformationDTO) {
        teacherService.deleteStudentInformation(studentInformationDTO);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
