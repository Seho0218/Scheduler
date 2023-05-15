package com.attendance.scheduler.Repository.jpa;

import com.attendance.scheduler.Entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchNotEmptyClassRepository extends JpaRepository<ClassEntity, String> {


    @Query("SELECT c.Monday, c.Tuesday, c.Wednesday, c.Thursday, c.Friday FROM ClassEntity c WHERE c.Monday <> 0 OR c.Tuesday <> 0 OR c.Wednesday <> 0 OR c.Thursday <> 0 OR c.Friday <> 0 ORDER BY c.Monday ASC, c.Tuesday ASC, c.Wednesday ASC, c.Thursday ASC, c.Friday ASC")
    List<Object[]> findClassesOrderByAsc();

}
