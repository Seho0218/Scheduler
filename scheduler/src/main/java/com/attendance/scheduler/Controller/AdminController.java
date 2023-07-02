package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.ApproveTeacherDTO;
import com.attendance.scheduler.Dto.Teacher.TeacherDTO;
import com.attendance.scheduler.Service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/")
public class AdminController {

    public final AdminService adminService;

    /*
     * TeacherList View
     * */
    @GetMapping("teacherList")
    public String teacherList(Model model) {
        List<TeacherDTO> teacherList = adminService.getTeacherList();
        model.addAttribute("teacherList", teacherList);
        return "admin/teacherList";
    }

    /*
     * grant Teacher Auth
     * */
    @PostMapping("grant")
    public ResponseEntity<String> grantAuth(ApproveTeacherDTO approveTeacherDTO) {
        adminService.approveAuth(approveTeacherDTO);
        return ResponseEntity.ok("승인되었습니다.");
    }

    /*
     * revoke Teacher Auth
     * */
    @PostMapping("revoke")
    public ResponseEntity<String> revokeAuth(ApproveTeacherDTO approveTeacherDTO) {
        adminService.revokeAuth(approveTeacherDTO);
        return ResponseEntity.ok("승인 취소되었습니다.");
    }
}
