package com.attendance.scheduler.Config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter @ToString
@RequiredArgsConstructor
public enum Role {

    ROLE_ADMIN("ADMIN"),
    ROLE_TEACHER("TEACHER"),
    ROLE_USER("USER");

    private final String role;
}
