package com.portfolio.goodjobs.repository.search;

import com.portfolio.goodjobs.domain.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobSearch {
    /*
    * 동적 쿼리를 활용해서 채용공고를 조회한다.
    * */

    Page<Job> searchAll(String[] types, String keyword, Pageable pageable);
}
