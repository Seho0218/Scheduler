package com.attendance.scheduler.Service;


import com.attendance.scheduler.Dto.ClassListDTO;

public interface SearchNotEmptyClassService {
      ClassListDTO findClassesOrderByAsc();
}
