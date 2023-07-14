package com.attendance.scheduler.Config.Authority;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class AdminVO {
    private final String username = "admin";
    private final String password = "123";

    public String getEncryptedPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
