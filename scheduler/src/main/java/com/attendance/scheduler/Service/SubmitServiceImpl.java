package com.attendance.scheduler.Service;

import com.attendance.scheduler.Dto.ClassDTO;
import com.attendance.scheduler.Repository.jpa.ClassSaveRepository;
import com.attendance.scheduler.Repository.jpa.SearchNotEmptyClassRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {

    private final ClassSaveRepository classSaveRepository;
    private final SearchNotEmptyClassRepository searchNotEmptyClassRepository;

    @Override
    public void saveClassTable(ClassDTO classDTO) {

        classValidator(classDTO);

        if(classDTO.getStudentName()!=null) {
            String trimmedStudentName = classDTO.getStudentName().trim();
            classDTO.setStudentName(trimmedStudentName);
            classSaveRepository.save(classDTO.toEntity());
        }
    }

    private void classValidator(ClassDTO classDTO) {
        Integer monday = classDTO.getMonday();
        Integer tuesday = classDTO.getTuesday();
        Integer wednesday = classDTO.getWednesday();
        Integer thursday = classDTO.getThursday();
        Integer friday = classDTO.getFriday();

        List<Object[]> classesOrderByAsc = searchNotEmptyClassRepository.findClassesOrderByAsc();

        for (Object[] row : classesOrderByAsc) {
            if(monday.equals(row[0])){
                throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
            }
            if(tuesday.equals(row[1])){
                throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
            }
            if(wednesday.equals(row[2])){
                throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
            }
            if(thursday.equals(row[3])){
                throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
            }
            if(friday.equals(row[4])){
                throw new IllegalStateException("이미 다른 원생이 신청했습니다.");
            }
        }
    }
}
