package com.attendance.scheduler.member.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class FindIdDTO {

    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;

    private String username;
}
