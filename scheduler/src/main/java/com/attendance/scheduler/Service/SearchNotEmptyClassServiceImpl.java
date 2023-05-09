package com.attendance.scheduler.Service;

import com.attendance.scheduler.Repository.jpa.SearchNotEmptyClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchNotEmptyClassServiceImpl implements SearchNotEmptyClassService {

    private final SearchNotEmptyClassRepository searchNotEmptyClassRepository;

    @Override
    public List<Integer> findByMondayClassesOrderByAsc() {
        return searchNotEmptyClassRepository.findMondayClassesOrderByAsc();
    }

    @Override
    public List<Integer> findByTuesdayClassesOrderByAsc() {
        return searchNotEmptyClassRepository.findTuesdayClassesOrderByAsc();
    }

    @Override
    public List<Integer> findByWednesdayClassesOrderByAsc() {
        return searchNotEmptyClassRepository.findWednesdayClassesOrderByAsc();
    }

    @Override
    public List<Integer> findByThursdayClassesOrderByAsc() {
        return searchNotEmptyClassRepository.findThursdayClassesOrderByAsc();
    }

    @Override
    public List<Integer> findByFridayClassesOrderByAsc() {
        return searchNotEmptyClassRepository.findFridayClassesOrderByAsc();
    }

}
