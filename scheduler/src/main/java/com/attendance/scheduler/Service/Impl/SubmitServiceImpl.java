package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.SubmitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {

    private final ClassTableRepository classTableRepository;

    @Override
    public void saveClassTable(ClassDTO classDTO) {

        classValidator(classDTO);

        if(classDTO.getStudentName()!=null) {
            String trimmedStudentName = classDTO.getStudentName().trim();
            classDTO.setStudentName(trimmedStudentName);
            classTableRepository.save(classDTO.toEntity());
        }
    }

    private void classValidator(ClassDTO classDTO) {

        List<Object[]> classesOrderByAsc = classTableRepository.findClassesOrderByAsc();
        ClassEntity byStudentNameIs = classTableRepository.findByStudentNameIs(classDTO.getStudentName().trim());

        log.info("classesOrderByAsc = {}", classesOrderByAsc.size());

        log.info("byStudentNameIs = " + byStudentNameIs.getStudentName());

        if(byStudentNameIs.getStudentName()!=null && classesOrderByAsc.size()!=0) {
            Integer[] daysOfWeek = {
                    classDTO.getMonday(),
                    classDTO.getTuesday(),
                    classDTO.getWednesday(),
                    classDTO.getThursday(),
                    classDTO.getFriday()
            };

            Integer[] studentClassList = {
                    byStudentNameIs.getMonday(),
                    byStudentNameIs.getTuesday(),
                    byStudentNameIs.getWednesday(),
                    byStudentNameIs.getThursday(),
                    byStudentNameIs.getFriday()
            };
            ArrayList<Integer> arrayListDaysOfWeek = new ArrayList<>(Arrays.asList(daysOfWeek));
            ArrayList<Integer> arrayStudentClassList = new ArrayList<>(Arrays.asList(studentClassList));

            log.info("arrayStudentClassList = {}", arrayStudentClassList);
            log.info("arrayListDaysOfWeek = {}", arrayListDaysOfWeek);

// 같은 값을 가진 요소를 제거
            arrayListDaysOfWeek.removeAll(arrayStudentClassList);

            String errorCode = "다른 원생과 겹치는 시간이 있습니다. 새로고침 후, 다시 신청해 주세요.";

            for (Object[] classDate : classesOrderByAsc) {
                for (Integer day : arrayListDaysOfWeek) {
                    if (Arrays.asList(classDate).contains(day)) {
                        throw new IllegalStateException(errorCode);
                    }
                }
            }
        }
    }
}
