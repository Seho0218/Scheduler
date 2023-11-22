package com.attendance.scheduler.member.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ApproveTeacherDTO {

    private String username;
    private boolean approved;
}
