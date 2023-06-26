package com.attendance.scheduler.Dto.Teacher;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class CertDTO {

    private String teacherId;

    @NotEmpty(message = "인증번호를 입력해 주세요")
    private String authNum;
}
