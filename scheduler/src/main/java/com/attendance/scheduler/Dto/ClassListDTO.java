package com.attendance.scheduler.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor
public class ClassListDTO {

    private List<Integer> mondayClassList = new ArrayList<>();
    private List<Integer> tuesdayClassList = new ArrayList<>();
    private List<Integer> wednesdayClassList = new ArrayList<>();
    private List<Integer> thursdayClassList = new ArrayList<>();
    private List<Integer> fridayClassList = new ArrayList<>();

    public static ClassListDTO getInstance(){
        return new ClassListDTO();
    }
}
