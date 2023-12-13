package com.attendance.scheduler.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChangeTeacherDTO {

    private Long teacherId;
    private Long studentId;
}
