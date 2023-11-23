package com.attendance.scheduler.member.teacher.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class EditEmailDTO {

    private String username;

    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;
}
