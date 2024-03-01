package com.portfolio.goodjobs.repository.search;

import com.portfolio.goodjobs.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.ZoneId;
import java.util.List;

/**
 * 동적 쿼리를 사용해서 채용공고를 검색한다.
 */
public interface JobSearch {

    ZoneId zoneId = ZoneId.of("Asia/Tokyo");

    Page<Job> searchAll(String[] locations, String keyword, boolean closed, Pageable pageable);
}
