package com.attendance.scheduler.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String adminId;

    @NotEmpty
    private String adminPassword;
}
