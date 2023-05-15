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
    public List<Object[]> findClassesOrderByAsc() {
        return searchNotEmptyClassRepository.findClassesOrderByAsc();
    }
}
