package com.attendance.scheduler.Dto.Teacher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class FindIdDTO {

    private String email;

    private String id;
}
