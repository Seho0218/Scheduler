package com.attendance.scheduler.Service;


import java.util.List;

public interface SearchNotEmptyClassService {
      List<Integer> findByMondayClassesOrderByAsc();
      List<Integer> findByTuesdayClassesOrderByAsc();
      List<Integer> findByWednesdayClassesOrderByAsc();
      List<Integer> findByThursdayClassesOrderByAsc();
      List<Integer> findByFridayClassesOrderByAsc();
}
