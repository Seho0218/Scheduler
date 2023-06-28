package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ClassTableRepository extends JpaRepository<ClassEntity, String> {

    /*
    * find Empty Class
    * */
    @Query("SELECT c.monday, c.tuesday, c.wednesday, c.thursday, c.friday " +
            "FROM ClassEntity c " +
            "WHERE c.monday <> 0 OR c.tuesday <> 0 OR c.wednesday <> 0 OR c.thursday <> 0 OR c.friday <> 0 " +
            "ORDER BY c.monday ASC, c.tuesday ASC, c.wednesday ASC, c.thursday ASC, c.friday ASC")
    List<Object[]> findClassesOrderByAsc();

    /*
    * find StudentClass by StudentName
    * */
    ClassEntity findByStudentNameIs(String studentName);

    /*
    * delete StudentName
    * */
    void deleteByStudentNameIn(List<String> studentName);

}
