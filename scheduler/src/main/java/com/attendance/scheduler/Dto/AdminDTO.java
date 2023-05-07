package com.attendance.scheduler.Dto;

import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor
public class AdminDTO {

    private Long id;

    private String adminId;

    private String adminPassword;
}
