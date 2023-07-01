package com.attendance.scheduler.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AdminCustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        String errorMessage;
        if(exception instanceof UsernameNotFoundException
                || exception instanceof InternalAuthenticationServiceException){
            errorMessage = "등록되지 않은 아이디거나 아이디를 입력해주세요";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 잘못되었습니다.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "관리자의 승인을 기다려주세요";
        } else {
            errorMessage = "관리자에게 문의해주세요";
        }
        log.info("errorMessage = {}", errorMessage);
        request.getSession().setAttribute("errorMessage", errorMessage);
        response.sendRedirect("/login/adminLogin");
    }
}