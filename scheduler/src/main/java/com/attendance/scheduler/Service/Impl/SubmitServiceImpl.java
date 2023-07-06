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
    private static final String errorCode = "다른 원생과 겹치는 시간이 있습니다. 새로고침 후, 다시 신청해 주세요.";

    @Override
    public void saveClassTable(ClassDTO classDTO) {

//        classValidator(classDTO);

        String trimmedStudentName = classDTO.getStudentName().trim();
        classDTO.setStudentName(trimmedStudentName);
        classTableRepository.save(classDTO.toEntity());

    }

    private void classValidator(ClassDTO classDTO) {

        List<Object[]> classesOrderByAsc = classTableRepository.findClassesOrderByAsc();
        ClassEntity byStudentNameIs = classTableRepository.findByStudentNameIs(classDTO.getStudentName().trim());

        Integer[] changedStudentClass = {
                classDTO.getMonday(),
                classDTO.getTuesday(),
                classDTO.getWednesday(),
                classDTO.getThursday(),
                classDTO.getFriday()
        };

        ArrayList<Integer> changedStudentClassList = new ArrayList<>(Arrays.asList(changedStudentClass));

        if(byStudentNameIs!=null) {

            log.info("byStudentNameIs = " + byStudentNameIs.getStudentName());

            Integer[] studentClass = {
                    byStudentNameIs.getMonday(),
                    byStudentNameIs.getTuesday(),
                    byStudentNameIs.getWednesday(),
                    byStudentNameIs.getThursday(),
                    byStudentNameIs.getFriday()
            };

            ArrayList<Integer> existStudentClassList = new ArrayList<>(Arrays.asList(studentClass));

            log.info("existStudentClassList = {}", existStudentClassList);
            log.info("changedStudentClass = {}", changedStudentClassList);

            for (Integer day : existStudentClassList) {
                changedStudentClassList.remove(day);
            }

            log.info("remainClass = {}", changedStudentClassList);

            duplicateClassCheck(classesOrderByAsc, changedStudentClassList);
        }

        duplicateClassCheck(classesOrderByAsc, changedStudentClassList);
    }



    private void duplicateClassCheck(List<Object[]> classesOrderByAsc, ArrayList<Integer> changedStudentClassList) {
        for (Object[] classDate : classesOrderByAsc) {

            for (Integer day : changedStudentClassList) {

                if (Arrays.asList(classDate).contains(day)) {

                    System.out.println("classDate = " + Arrays.toString(classDate));
                    System.out.println("day = " + day);
                }
            }
        }
    }
}
