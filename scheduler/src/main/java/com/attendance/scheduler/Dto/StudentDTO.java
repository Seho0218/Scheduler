package com.attendance.scheduler.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudentDTO {

    @NotEmpty
    private Long id;

    private String studentName;

    private String studentTel;

    private boolean attendanceChecked;

}
