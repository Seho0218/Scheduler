package com.attendance.scheduler.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClassTableRepository extends JpaRepository<ClassEntity, Long> {


    /*
    * find StudentClass by StudentName
    * */
    boolean existsByStudentNameIs(String studentName);

    Optional<ClassEntity> findByStudentNameIs(String studentName);


    /*
    * delete StudentName
    * */
    @Transactional
    void deleteByStudentName(String studentName);

    @Transactional
    void deleteByStudentNameIn(List<String> studentName);

}