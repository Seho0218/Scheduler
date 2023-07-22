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

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {
    private final ClassTableRepository classTableRepository;

    private static void duplicateClassValidator(ClassDTO classDTO, List<Object[]> classesOrderByAsc) {
        String errorCode = "다른 원생과 겹치는 시간이 있습니다. 새로고침 후, 다시 신청해 주세요.";

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

    @Override
    @Transactional
    public void saveClassTable(ClassDTO classDTO) {
        classValidator(classDTO);
        String trimmedStudentName = classDTO.getStudentName().trim();
        classDTO.setStudentName(trimmedStudentName);
        classTableRepository.save(classDTO.toEntity());
    }

    private void classValidator(ClassDTO classDTO) {
        List<Object[]> classesOrderByAsc = classTableRepository.findClassesOrderByAsc();
        ClassEntity byStudentNameIs = classTableRepository.findByStudentNameIs(classDTO.getStudentName());
        log.info("byStudentNameIs={}", byStudentNameIs);
        log.info("classDTO={}", classDTO);

        if (byStudentNameIs == null) {
            duplicateClassValidator(classDTO, classesOrderByAsc);
        } else {
            log.info("check");
            classTableRepository.deleteByStudentName(classDTO.getStudentName());
            duplicateClassValidator(classDTO, classesOrderByAsc);
        }
    }
}
