package com.attendance.scheduler.Dto;

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
