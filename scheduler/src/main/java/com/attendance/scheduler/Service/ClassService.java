package com.attendance.scheduler.Service;


import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Dto.ClassListDTO;
import com.attendance.scheduler.Dto.StudentClassDTO;
import com.attendance.scheduler.Dto.Teacher.DeleteClassDTO;

import java.util.List;

public interface ClassService {

      // 수업 유무 조회
      List<ClassDTO> findClassByStudent();

      ClassListDTO findAllClasses();

      StudentClassDTO findStudentClasses(StudentClassDTO studentClassDTO);

      void saveClassTable(ClassDTO classDTO);

      // 수업 삭제
      void deleteClass(DeleteClassDTO deleteClassDTO);

}
