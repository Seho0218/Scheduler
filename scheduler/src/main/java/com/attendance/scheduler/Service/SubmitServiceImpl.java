package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Repository.jpa.ClassTableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {

    private final ClassTableRepository classTableRepository;

    @Override
    public void saveClassTable(ClassDTO classDTO) {

//        classValidator(classDTO);

        if(classDTO.getStudentName()!=null) {
            String trimmedStudentName = classDTO.getStudentName().trim();
            classDTO.setStudentName(trimmedStudentName);
            classTableRepository.save(classDTO.toEntity());
        }
    }

    private void classValidator(ClassDTO classDTO) {

        List<Object[]> classesOrderByAsc = classTableRepository.findClassesOrderByAsc();

        Integer[] daysOfWeek = {
                classDTO.getMonday(),
                classDTO.getTuesday(),
                classDTO.getWednesday(),
                classDTO.getThursday(),
                classDTO.getFriday()
        };

        String errorCode = "다른 원생과 겹치는 시간이 있습니다. 새로고침 후, 다시 신청해 주세요.";

        for (Object[] classDate : classesOrderByAsc) {
            for (Integer day : daysOfWeek) {
                if (Arrays.asList(classDate).contains(day)) {
                    throw new IllegalStateException(errorCode);
                }
            }
        }
    }
}
