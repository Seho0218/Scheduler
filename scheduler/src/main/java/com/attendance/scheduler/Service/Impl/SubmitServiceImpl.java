package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.SubmitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.transaction.annotation.Isolation.REPEATABLE_READ;

@Slf4j
@Service
@Transactional(isolation = REPEATABLE_READ)
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {

    private final ClassTableRepository classTableRepository;
    private static final String errorCode = "다른 원생과 겹치는 시간이 있습니다. 새로고침 후, 다시 신청해 주세요.";

    @Override
    public void saveClassTable(ClassDTO classDTO) {

        classValidator(classDTO);
        String trimmedStudentName = classDTO.getStudentName().trim();
        classDTO.setStudentName(trimmedStudentName);
        classTableRepository.save(classDTO.toEntity());

    }

    private static void duplicateClassValidator(ClassDTO classDTO, List<Object[]> classesOrderByAsc) {
        for (Object[] row : classesOrderByAsc) {
            Integer mondayValue = (Integer) row[0];
            Integer tuesdayValue = (Integer) row[1];
            Integer wednesdayValue = (Integer) row[2];
            Integer thursdayValue = (Integer) row[3];
            Integer fridayValue = (Integer) row[4];

            if (mondayValue.equals(classDTO.getMonday())) throw new RuntimeException(errorCode);
            if (tuesdayValue.equals(classDTO.getTuesday())) throw new RuntimeException(errorCode);
            if (wednesdayValue.equals(classDTO.getWednesday())) throw new RuntimeException(errorCode);
            if (thursdayValue.equals(classDTO.getThursday())) throw new RuntimeException(errorCode);
            if (fridayValue.equals(classDTO.getFriday())) throw new RuntimeException(errorCode);
        }
    }

    private void classValidator(ClassDTO classDTO) {

        List<Object[]> classesOrderByAsc = classTableRepository.findClassesOrderByAsc();
        ClassEntity byStudentNameIs = classTableRepository.findByStudentNameIs(classDTO.getStudentName().trim());

        if (byStudentNameIs == null) {
            duplicateClassValidator(classDTO, classesOrderByAsc);
        } else {
            log.info("check");
            classTableRepository.deleteByStudentNameIn(singletonList(byStudentNameIs.getStudentName()));
            duplicateClassValidator(classDTO, classesOrderByAsc);
        }
    }
}
