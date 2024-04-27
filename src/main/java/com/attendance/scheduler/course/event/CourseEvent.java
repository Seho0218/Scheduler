package com.attendance.scheduler.course.event;

import com.attendance.scheduler.teacher.domain.TeacherEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CourseEvent {
    private final TeacherEntity teacherEntity;
    private final String message;
}
