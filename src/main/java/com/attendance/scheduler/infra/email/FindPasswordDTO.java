package com.attendance.scheduler.infra.email;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindPasswordDTO {

    @NotEmpty(message = "이메일을 입력해 주세요")
    private String email;

    @NotEmpty(message = "아이디을 입력해 주세요")
    private String username;
}
