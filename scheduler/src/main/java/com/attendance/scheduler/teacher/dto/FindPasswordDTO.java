package com.attendance.scheduler.teacher.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class FindPasswordDTO {

    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;

    @NotEmpty(message = "아이디을 입력해 주세요")
    private String username;
}
