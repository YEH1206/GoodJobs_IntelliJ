package com.portfolio.goodjobs.repository;

import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.repository.search.JobSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long>, JobSearch {
}
