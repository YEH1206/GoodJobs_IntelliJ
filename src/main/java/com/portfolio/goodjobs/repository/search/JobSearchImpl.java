package com.portfolio.goodjobs.repository.search;

import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.domain.QJob;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class JobSearchImpl extends QuerydslRepositorySupport implements JobSearch {

    public JobSearchImpl() {
        super(Job.class);
    }

    @Override
    public Page<Job> searchAll(String[] types, String keyword, Pageable pageable) {

        QJob job = QJob.job;
        JPQLQuery<Job> query = from(job);

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type: types) {

                switch(type) {
                    case "t":
                        booleanBuilder.or(job.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(job.companyName.contains(keyword));
                        break;
                    case "a":
                        booleanBuilder.or(job.address.contains(keyword));
                }
            }// end of for
            query.where(booleanBuilder);
        }// end of if

        // 공고번호 > 0
        query.where(job.no.gt(0L));

        // paging
        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, query);

        List<Job> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }
}
