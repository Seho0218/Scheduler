package com.attendance.scheduler.Controller;

import com.attendance.scheduler.Dto.Admin.AdminCertDTO;
import com.attendance.scheduler.Dto.Admin.AdminDTO;
import com.attendance.scheduler.Service.CertService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Controller
@RequestMapping("/cert/*")
@RequiredArgsConstructor
public class CertController {

    private final CertService certService;

    /*
    아이디 찾기
     */
    // 아이디 찾기 폼
    @GetMapping("findId")
    public String findId(Model model) {
        model.addAttribute("login", new AdminDTO());
        return "/cert/FindId";
    }

    // 메일로 아이디 보내기
    @PostMapping("sendUserId")
    public ResponseEntity<Object> sendEmail(AdminCertDTO adminCertDTO) {

        String adminId = certService.findIdByEmail(adminCertDTO);

        if (adminId.isEmpty()) {
            return new ResponseEntity<>("등록된 정보가 없습니다.",BAD_REQUEST);
        }

        String adminEmail = adminCertDTO.getAdminEmail();
        AdminCertDTO certDTO = AdminCertDTO.getInstance();

        certDTO.setAdminId(adminId);
        certDTO.setAdminEmail(adminEmail);
        certService.sendUserId(certDTO);

        return new ResponseEntity<>("등록하신 이메일로 아이디가 전송되었습니다.",OK);
    }

    /*
    비밀번호 찾기
     */

    // 비밀번호 찾기 폼
    @GetMapping("findPassword")
    public String findPassword(Model model) {
        model.addAttribute("login", new AdminDTO());
        return "/cert/FindPassword";
    }

    @GetMapping("overlapCheck")
    public int overlapCheck(AdminCertDTO adminCertDTO) {
        return certService.overlapCheck(adminCertDTO);
    }

    @GetMapping("emailCheck")
    public ResponseEntity<Boolean> emailCheck(AdminCertDTO adminCertDTO){
        boolean emailCheck = certService.emailCheck(adminCertDTO);
        return new ResponseEntity<>(emailCheck, OK);
    }


    // 인증번호 보내기 페이지
    @GetMapping("FindPwd_auth")
    public String auth(AdminCertDTO adminCertDTO, HttpSession session) {

        String adminId = adminCertDTO.getAdminId();

        Map<String, Object> authStatus = (Map<String, Object>) session.getAttribute("authStatus");
        if(authStatus == null || !adminId.equals(authStatus.get("adminId"))) {
            return "/cert/FindPwd";
        }
        return "/cert/FindPwd_auth";
    }

    @PostMapping("FindPwd_auth")
    public ResponseEntity<Object> authenticateUser(AdminCertDTO adminCertDTO, HttpSession session) {

        Map<String, Object> authStatus = new HashMap<>();
        String adminId = adminCertDTO.getAdminId();

        authStatus.put("adminId", adminId);
        authStatus.put("status", false);

        session.setMaxInactiveInterval(300);
        session.setAttribute("authStatus", authStatus);

        return new ResponseEntity<>(adminId, OK);
    }


    // 인증번호 보내기
    @PostMapping("authNum")
    private ResponseEntity<String> authNum(String userEmail, HttpSession session){
        StringBuilder authNum = new StringBuilder();
        for(int i=0;i<6;i++) {
            authNum.append((int) (Math.random() * 10));
        }

        log.info("인증번호={}", authNum);
        log.info("이메일={} ", userEmail);

        if(userEmail != null) {
            certService.sendAuthNum(userEmail, authNum.toString());
        }

        Map<String, Object> authNumMap = new HashMap<>();

        long createTime = System.currentTimeMillis();
        long endTime = createTime + (300 *1000);


        authNumMap.put("createTime", createTime);
        authNumMap.put("endTime", endTime);
        authNumMap.put("authNum", authNum.toString());

        session.setMaxInactiveInterval(300);
        session.setAttribute("authNum", authNumMap);

        return new ResponseEntity<>("인증번호가 전송되었습니다", OK);
    }

    // 인증번호가 맞는지 확인
    @PostMapping("authNumCheck")
    private ResponseEntity<String> authNumCheck(String authNum, HttpSession session){

        Map<String, Object> sessionAuthNumMap = (Map<String, Object>) session.getAttribute("authNum");
        String msg;

        if(sessionAuthNumMap == null) {
            msg = "인증번호를 전송해주세요";
            return new ResponseEntity<>(msg, BAD_REQUEST);
        }

        // 인증번호 만료시간
        long endTime = (long) sessionAuthNumMap.get("endTime");

        // 현재시간이 만료시간이 지났다면
        if(System.currentTimeMillis() > endTime) {
            msg = "인증시간이 만료되었습니다";
            session.setAttribute(authNum, null);
            session.setMaxInactiveInterval(0);
            return new ResponseEntity<>(msg, BAD_REQUEST);
        }

        // 인증번호
        String sessionAuthNum = (String) sessionAuthNumMap.get("authNum");
        if(!authNum.equals(sessionAuthNum)) {
            msg = "인증번호가 일치하지 않습니다";
            return new ResponseEntity<>(msg, BAD_REQUEST);
        } else {
            // 인증번호가 일치하면
            return new ResponseEntity<>(OK);
        }
    }

    // 인증 완료 후
    @PostMapping("authCOM")
    public ResponseEntity<String> authCompletion(HttpSession session) {
        Map<String, Object> authStatus = (Map<String, Object>) session.getAttribute("authStatus");
        if(authStatus == null) {
            return new ResponseEntity<>("인증시간이 만료되었습니다", BAD_REQUEST);
        }
        authStatus.put("status", true);
        return new ResponseEntity<>(OK);
    }

    //TODO 비밀번호 변경 페이지



    //TODO 비밀번호 변경

}
