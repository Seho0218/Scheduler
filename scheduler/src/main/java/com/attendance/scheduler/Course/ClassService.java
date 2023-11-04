package com.attendance.scheduler.Course;


import com.attendance.scheduler.Course.Dto.ClassDTO;
import com.attendance.scheduler.Course.Dto.StudentClassDTO;
import com.attendance.scheduler.Student.Dto.ClassListDTO;
import com.attendance.scheduler.Teacher.Dto.DeleteClassDTO;

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
