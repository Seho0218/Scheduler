package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.DeleteClassDTO;
import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Service.ManageService;
import com.attendance.scheduler.Service.SearchClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/manage/*")
@RequiredArgsConstructor
public class ManageController {

    // 관리자 로직
    private final ManageService manageService;

    //수업 조회
    private final SearchClassService searchClassService;

    // 조회
    @GetMapping("class")
    public String managePage(Model model){

        List<ClassDTO> classTable = searchClassService.findClassTable();
        model.addAttribute("classList", new DeleteClassDTO());
        model.addAttribute("findClassTable", classTable);
        log.info("student = {}", searchClassService.findClassTable());
        return "manage/class";
    }

    // 삭제
    @PostMapping("delete")
    public ResponseEntity<String> deleteSchedule(@ModelAttribute("classList") DeleteClassDTO deleteClassDTO){

        manageService.deleteClass(deleteClassDTO);
        log.info("delete_List={}", deleteClassDTO.getDeleteClassList());
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
