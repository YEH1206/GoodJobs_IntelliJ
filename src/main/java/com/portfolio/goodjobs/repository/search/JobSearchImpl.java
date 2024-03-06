package com.portfolio.goodjobs.repository.search;

import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.domain.QJob;
import com.portfolio.goodjobs.domain.QJobLocation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class JobSearchImpl extends QuerydslRepositorySupport implements JobSearch {

    public JobSearchImpl() {
        super(Job.class);
    }

    @Override
    public Page<Job> searchAll(String[] locations, String keyword, boolean closed, Pageable pageable) {

        QJob job = QJob.job;
        QJobLocation jobLocation = QJobLocation.jobLocation;

        JPQLQuery<Job> jobJPQLQuery = from(job)
                .leftJoin(job.locationSet, jobLocation)
                .groupBy(job.no);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 지역별 검색
        if (locations != null && locations.length > 0) {
            booleanBuilder.and(jobLocation.sigungu.stringValue().in(locations));
        }

        // 키워드 검색
        if (keyword != null && !keyword.isEmpty()) {
            String[] keywords = keyword.split("\\s+");

            // 키워드 검색용 boolean 생성
            BooleanBuilder keywordBoolean = new BooleanBuilder();

            for (String kw : keywords) {
                keywordBoolean.or(      // 공백으로 구분된 키워드를 or로 연결
                        job.title.containsIgnoreCase(kw)        // 키워드 검색 범위: 제목 or 회사명 or 상세내용
                                .or(job.companyName.containsIgnoreCase(kw))
                                .or(job.detail.containsIgnoreCase(kw)));
            }

            // 키워드 검색 결과를 기존의 boolean과 and로 연결
            booleanBuilder.and(keywordBoolean);
        }

        // 현재 채용중인 공고만 검색
        if (!closed) {
            LocalDateTime current = LocalDateTime.now(zoneId);
            booleanBuilder.and(job.deadline.after(current));
        }

        jobJPQLQuery.where(booleanBuilder);

        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, jobJPQLQuery);

        List<Job> list = jobJPQLQuery.fetch();

        long count = jobJPQLQuery.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
