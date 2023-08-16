package com.attendance.scheduler.Service.Impl;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Entity.ClassEntity;
import com.attendance.scheduler.Mapper.ClassMapper;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import com.attendance.scheduler.Service.SubmitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {
    private final ClassMapper classMapper;
    private final ClassTableRepository classTableRepository;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveClassTable(ClassDTO classDTO) {
        classValidator(classDTO);
        String trimmedStudentName = classDTO.getStudentName().trim();
        classDTO.setStudentName(trimmedStudentName);
        classTableRepository.save(classDTO.toEntity());
    }

    private void classValidator(ClassDTO classDTO) {
        log.info("classDTO={}", classDTO);
        ClassEntity byStudentNameIs = classTableRepository.findByStudentNameIs(classDTO.getStudentName());
        log.info("byStudentNameIs={}", byStudentNameIs);

        if (byStudentNameIs != null) {
            duplicateClassValidator(classDTO);
        }
        classTableRepository.deleteByStudentName(classDTO.getStudentName());
        duplicateClassValidator(classDTO);

    }

    private void duplicateClassValidator(ClassDTO classDTO) {
        String errorCode = "다른 원생과 겹치는 시간이 있습니다. 새로고침 후, 다시 신청해 주세요.";

        List<ClassDTO> allClassDTO = classTableRepository.findAll()
                .stream()
                .map(classMapper::toClassDTO)
                .toList();

        for (ClassDTO classDTOList: allClassDTO) {
            Integer mondayValue = classDTOList.getMonday();
            Integer tuesdayValue = classDTOList.getTuesday();
            Integer wednesdayValue = classDTOList.getWednesday();
            Integer thursdayValue = classDTOList.getThursday();
            Integer fridayValue = classDTOList.getFriday();

            if (mondayValue.equals(classDTO.getMonday())) throw new RuntimeException(errorCode);
            if (tuesdayValue.equals(classDTO.getTuesday())) throw new RuntimeException(errorCode);
            if (wednesdayValue.equals(classDTO.getWednesday())) throw new RuntimeException(errorCode);
            if (thursdayValue.equals(classDTO.getThursday())) throw new RuntimeException(errorCode);
            if (fridayValue.equals(classDTO.getFriday())) throw new RuntimeException(errorCode);
        }
    }
}