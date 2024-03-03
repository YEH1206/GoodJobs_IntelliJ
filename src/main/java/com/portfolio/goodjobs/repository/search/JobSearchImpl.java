package com.portfolio.goodjobs.repository.search;

import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.domain.JobLocation;
import com.portfolio.goodjobs.domain.QJob;
import com.portfolio.goodjobs.domain.QJobLocation;
import com.portfolio.goodjobs.dto.JobListDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class JobSearchImpl extends QuerydslRepositorySupport implements JobSearch {

    public JobSearchImpl(JPAQueryFactory queryFactory) {
        super(Job.class);
    }

    @Override
    public Page<Job> searchAll(String[] locations, String keyword, boolean closed, Pageable pageable) {

        QJob job = QJob.job;
        QJobLocation jobLocation = QJobLocation.jobLocation;

        JPQLQuery<Job> jobJPQLQuery = from(job);
        jobJPQLQuery.leftJoin(jobLocation).on(jobLocation.job.eq(job));
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 지역별 검색
        if (locations != null && locations.length > 0) {
            booleanBuilder.and(jobLocation.sigungu.stringValue().in(locations));
        }

        // 키워드 검색
        if (keyword != null && !keyword.isEmpty()) {
            String[] keywords = keyword.split("\\s+");

            for (String kw : keywords) {
                booleanBuilder.and(
                        job.title.containsIgnoreCase(kw)
                                .or(job.companyName.containsIgnoreCase(kw))
                                .or(job.detail.containsIgnoreCase(kw)));
            }
        }

        // 채용중인 공고 검색
        if (!closed) {
            LocalDateTime current = LocalDateTime.now(zoneId);
            booleanBuilder.and(job.deadline.after(current));
        }

        jobJPQLQuery.where(booleanBuilder);

        Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, jobJPQLQuery);

        List<Job> jobList = jobJPQLQuery.fetch();

        long totalCount = jobJPQLQuery.fetchCount();

        return new PageImpl<>(jobList, pageable, totalCount);
    }
}
