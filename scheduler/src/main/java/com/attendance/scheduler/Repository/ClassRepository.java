package com.attendance.scheduler.Repository;

import com.attendance.scheduler.Entity.ClassEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class ClassRepository {

    @PersistenceContext EntityManager em;

    public void saveClassTable(ClassEntity classEntity) {

        log.info("student={} updateTime={}", classEntity.getStudentName(), classEntity.getUpdateTimeStamp());

        if(classEntity.getStudentName() == null) {
            em.persist(classEntity);
        }else {
            em.merge(classEntity);
        }
    }
}
