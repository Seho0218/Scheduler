package com.attendance.scheduler.Dto.Admin;

import com.attendance.scheduler.Entity.AdminEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AdminCertDTO {

    private String adminId;

    private String adminPassword;

    private String changedPwd;

    private String adminEmail;

    public static AdminCertDTO getInstance(){
        return new AdminCertDTO();
    }

    public AdminEntity toEntity(){
        return AdminEntity.builder()
                .adminId(adminId)
                .adminPassword(adminPassword)
                .adminEmail(adminEmail)
                .build();
    }
}
