package com.attendance.scheduler.Dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ClassListDTO {

    private List<Integer> ClassInMondayList;
    private List<Integer> ClassInTuesdayList;
    private List<Integer> ClassInWednesdayList;
    private List<Integer> ClassInThursdayList;
    private List<Integer> ClassInFridayList;


    public static ClassListDTO classTableInstance(){
        return new ClassListDTO();
    }
}
