package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.config.RootConfig;
import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.dto.JobDto;
import com.portfolio.goodjobs.dto.PageRequestDto;
import com.portfolio.goodjobs.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

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

    default Job dtoToEntity(JobDto jobDto) {

        Job job = modelMapper().map(jobDto, Job.class);

        return job;
    }

    default JobDto entityToDto(Job job) {

        JobDto jobDto = modelMapper().map(job, JobDto.class);

        return jobDto;
    }
}
