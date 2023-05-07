package com.attendance.scheduler.Dto;

import com.attendance.scheduler.Entity.ClassEntity;
import lombok.*;

import java.sql.Timestamp;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ClassDTO {

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

    private Timestamp updateTimeStamp;

    public ClassEntity toEntity() {
        return ClassEntity.builder()
                .studentName(studentName)
                .counts(counts)
                .monday(Monday)
                .tuesday(Tuesday)
                .wednesday(Wednesday)
                .thursday(Thursday)
                .friday(Friday)
                .updateTimeStamp(updateTimeStamp)
                .build();
    }
}
