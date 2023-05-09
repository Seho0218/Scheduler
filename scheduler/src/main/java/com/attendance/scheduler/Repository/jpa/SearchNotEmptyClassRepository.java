package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchNotEmptyClassRepository extends JpaRepository<ClassEntity, String> {

    @Query("SELECT c.Monday FROM ClassEntity c WHERE c.Monday <> 0 ORDER BY c.Monday ASC")
    List<Integer> findMondayClassesOrderByAsc();

    @Query("SELECT c.Tuesday FROM ClassEntity c WHERE c.Tuesday <> 0 ORDER BY c.Tuesday ASC")
    List<Integer> findTuesdayClassesOrderByAsc();

    @Query("SELECT c.Wednesday FROM ClassEntity c WHERE c.Wednesday <> 0 ORDER BY c.Wednesday ASC")
    List<Integer> findWednesdayClassesOrderByAsc();

    @Query("SELECT c.Thursday FROM ClassEntity c WHERE c.Thursday <> 0 ORDER BY c.Thursday ASC")
    List<Integer> findThursdayClassesOrderByAsc();

    @Query("SELECT c.Friday FROM ClassEntity c WHERE c.Friday <> 0 ORDER BY c.Friday ASC")
    List<Integer> findFridayClassesOrderByAsc();

}
