package com.attendance.scheduler.course.application;


import com.attendance.scheduler.course.dto.ClassDTO;
import com.attendance.scheduler.course.dto.StudentClassDTO;
import com.attendance.scheduler.student.dto.ClassListDTO;
import com.attendance.scheduler.teacher.dto.DeleteClassDTO;

import java.util.List;
import java.util.Optional;

public interface ClassService {

      // 수업 유무 조회
      List<ClassDTO> findClassByStudent();

      ClassListDTO findAllClasses();

      Optional<StudentClassDTO> findStudentClasses(StudentClassDTO studentClassDTO);


      void saveClassTable(ClassDTO classDTO);

      // 수업 삭제
      void deleteClass(DeleteClassDTO deleteClassDTO);

}
