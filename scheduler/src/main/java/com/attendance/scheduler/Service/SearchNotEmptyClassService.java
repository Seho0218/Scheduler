package com.attendance.scheduler.Service;


import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;

public interface SearchNotEmptyClassService {
      ClassListDTO findClassesOrderByAsc();

      StudentClassDTO findStudentClasses(StudentClassDTO studentClassDTO);

}
