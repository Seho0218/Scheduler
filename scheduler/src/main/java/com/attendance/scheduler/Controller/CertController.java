package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.AdminCertDTO;
import com.attendance.scheduler.Service.CertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CertController {

    private final CertService certService;

    @GetMapping("FindId")
    public String FindId() {
        return "FindId";
    }

    // 메일로 아이디 보내기
    @PostMapping("sendUserId")
    public ResponseEntity<Object> sendEmail(AdminCertDTO adminCertDTO){

        String adminId = certService.FindId(adminCertDTO);
        String adminEmail = adminCertDTO.getAdminEmail();
        AdminCertDTO certDTO = AdminCertDTO.getInstance();

        certDTO.setAdminId(adminId);
        certDTO.setAdminEmail(adminEmail);

        if(!adminId.isEmpty()) {
            certService.sendUserId(certDTO);
        }

        return new ResponseEntity<>(OK);
    }
}
