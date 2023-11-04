package com.attendance.scheduler.Teacher.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor
public class DeleteClassDTO {

    List<String> deleteClassList;
}
