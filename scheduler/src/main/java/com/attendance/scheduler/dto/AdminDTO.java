package com.attendance.scheduler.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDTO {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String adminId;

    @NotEmpty
    private String adminPassword;
}
