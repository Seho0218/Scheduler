package com.attendance.scheduler.Service;


import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;

import java.util.List;

public interface SearchClassService {

      // 수업 유무 조회
      List<ClassDTO> findClassTable();

      ClassListDTO findClassesOrderByAsc();

      StudentClassDTO findStudentClasses(StudentClassDTO studentClassDTO);

}
