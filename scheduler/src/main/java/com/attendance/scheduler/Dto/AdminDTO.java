package com.attendance.scheduler.Dto;

import com.attendance.scheduler.Entity.AdminEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AdminDTO {

    @NotEmpty(message = "아이디를 입력해 주세요")
    private String adminId;

    @NotEmpty(message = "이름을 입력해 주세요")
    private String adminName;

    @NotEmpty(message = "비밀번호를 입력해 주세요")
    private String adminPassword;

    @NotEmpty(message = "이메일을 입력해 주세요")
    private String adminEmail;

    public static AdminDTO getInstance(){
        return new AdminDTO();
    }

    public AdminEntity toEntity(){
        return AdminEntity.builder()
                .adminId(adminId)
                .adminPassword(adminPassword)
                .adminEmail(adminEmail)
                .build();
    }

}
