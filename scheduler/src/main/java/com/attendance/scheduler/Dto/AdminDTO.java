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

    @NotEmpty
    private String adminId;

    @NotEmpty
    private String adminPassword;

    @NotEmpty
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
