package com.attendance.scheduler.Dto.Admin;

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

    public static AdminCertDTO getInstance(){
        return new AdminCertDTO();
    }

}
