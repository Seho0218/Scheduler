package com.attendance.scheduler.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ClassDTO {

//    학생 이름
    private String studentName;
//    수업 횟수
    private String counts;

//    수업 시간
    private int Monday;
    private int Tuesday;
    private int Wednesday;
    private int Thursday;
    private int Friday;

}
