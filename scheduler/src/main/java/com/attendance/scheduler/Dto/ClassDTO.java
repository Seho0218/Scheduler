package com.attendance.scheduler.Dto;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ClassDTO {

    private Long student_id;
//    학생 이름
    private String studentName;
//    수업 횟수
    private int counts;

//    수업 시간
    private int Monday;
    private int Tuesday;
    private int Wednesday;
    private int Thursday;
    private int Friday;

    public ClassDTO(Long student_id, String studentName, int counts, int monday, int tuesday, int wednesday, int thursday, int friday) {
        this.student_id = student_id;
        this.studentName = studentName;
        this.counts = counts;
        Monday = monday;
        Tuesday = tuesday;
        Wednesday = wednesday;
        Thursday = thursday;
        Friday = friday;
    }

}
