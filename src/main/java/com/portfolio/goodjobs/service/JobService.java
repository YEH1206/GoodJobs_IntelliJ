package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.config.RootConfig;
import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.dto.JobDto;
import com.portfolio.goodjobs.dto.PageRequestDto;
import com.portfolio.goodjobs.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public interface JobService {

    ModelMapper modelMapper();

    /*
    * 채용공고를 DB에 저장하고, 생성된 공고번호를 반환한다.
    * */
    Long register(JobDto jobDto);

    /*
    * 공고번호로 채용공고를 조회하고, Dto로 변환해 반환한다.
    * */
    JobDto readOne(Long no);

    /*
    * Dto의 공고번호로 채용공고를 조회하고, 해당 채용공고를 수정·저장한다.
    * */
    void modify(JobDto jobDto);

    /*
    * 공고번호로 채용공고를 조회하고, 해당 채용공고를 삭제한다.
    * */
    void remove(Long no);

    /*
    * 검색조건에 해당하는 채용공고를 조회하고, 목록을 반환한다.
    * */
    PageResponseDto<JobDto> list(PageRequestDto pageRequestDto);

    /**
     * Instant 타입의 deadline을 LocalDateTime으로 변환하고, Job 객체에 저장한다.
     */
    default Job dtoToEntity(JobDto jobDto) {

        Job job = modelMapper().map(jobDto, Job.class);

        // "Asia/Tokyo" 시간대를 활용해 LocalDateTime 객체를 생성한다.
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        LocalDateTime deadline = LocalDateTime.ofInstant(jobDto.getDeadline(), zoneId);

        // deadline을 '9999-12-31T23:59' 이내로 제한한다.
        LocalDateTime max = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
        job.setDeadline(deadline.isBefore(max) ? deadline : max);

        return job;
    }

    /**
     * LocalDateTime 타입의 deadline을 Instant 타입으로 변환하고, JobDto 객체에 저장한다.
     */
    default JobDto entityToDto(Job job) {

        JobDto jobDto = modelMapper().map(job, JobDto.class);

        // "Asia/Tokyo" 시간대를 활용해 Instant 객체를 생성한다.
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        Instant deadline = job.getDeadline().atZone(zoneId).toInstant();
        jobDto.setDeadline(deadline);

        return jobDto;
    }
}
