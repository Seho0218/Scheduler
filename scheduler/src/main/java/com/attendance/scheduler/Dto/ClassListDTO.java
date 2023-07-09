package com.attendance.scheduler.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ClassListDTO {

    private List<Integer> mondayClassList;
    private List<Integer> tuesdayClassList;
    private List<Integer> wednesdayClassList;
    private List<Integer> thursdayClassList;
    private List<Integer> fridayClassList;


    public static ClassListDTO getInstance(){
        return new ClassListDTO();
    }
}
