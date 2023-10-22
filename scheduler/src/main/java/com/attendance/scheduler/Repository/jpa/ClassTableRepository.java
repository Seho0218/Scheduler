package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClassTableRepository extends JpaRepository<ClassEntity, String> {


    /*
    * find StudentClass by StudentName
    * */
    ClassEntity findByStudentNameIs(String studentName);

    /*
    * delete StudentName
    * */
    @Transactional
    void deleteByStudentName(String studentName);

    @Transactional
    void deleteByStudentNameIn(List<String> studentName);

}