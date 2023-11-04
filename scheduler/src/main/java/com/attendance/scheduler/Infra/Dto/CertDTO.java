package com.attendance.scheduler.Infra.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class CertDTO {

    private String username;

    @NotEmpty(message = "인증번호를 입력해 주세요")
    private String authNum;
}
