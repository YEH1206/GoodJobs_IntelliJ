package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.domain.QJob;
import com.portfolio.goodjobs.domain.QJobLocation;
import com.portfolio.goodjobs.dto.JobDto;
import com.portfolio.goodjobs.dto.JobListDto;
import com.portfolio.goodjobs.dto.PageRequestDto;
import com.portfolio.goodjobs.dto.PageResponseDto;
import com.portfolio.goodjobs.repository.JobRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final ModelMapper modelMapper;

    private final JobRepository jobRepository;

    @Override
    public ModelMapper modelMapper() {
        return modelMapper;
    }

    @Override
    public Long register(JobDto jobDto) {

        Job job = dtoToEntity(jobDto);

        return jobRepository.save(job).getNo();
    }

    @Override
    public JobDto readOne(Long no) {

        Optional<Job> result = jobRepository.findById(no);

        Job job = result.orElseThrow();

        return entityToDto(job, JobDto.class);
    }

    @Override
    @Transactional
    public PageResponseDto<JobListDto> jobList(PageRequestDto pageRequestDto) {

        String[] locations = pageRequestDto.getLocations();
        String keyword = pageRequestDto.getKeyword();
        boolean closed = pageRequestDto.isClosed();
        Pageable pageable = pageRequestDto.getPageable();

        Page<Job> result = jobRepository.searchAll(locations, keyword, closed, pageable);
        List<JobListDto> dtoList = result.getContent().stream().map(job -> entityToDto(job, JobListDto.class)).toList();

        return PageResponseDto.<JobListDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public void modify(JobDto jobDto) {
        Optional<Job> result = jobRepository.findById(jobDto.getNo());

        Job job = result.orElseThrow();

        // 기본사항 수정
        job.change(jobDto.getLogoFileName(), jobDto.getAddress(), jobDto.getTitle(),
                jobDto.isExp(), jobDto.getExpYear(), jobDto.getEdu(), jobDto.getDetail());

        // 마감일 수정
        Job newJob = dtoToEntity(jobDto);
        job.setDeadline(newJob.getDeadline());

        // 근무지역 수정
        job.clearLocations();
        jobDto.getLocations().forEach(job::addLocation);

        jobRepository.save(job);
    }

    @Override
    public void remove(Long no) {
        jobRepository.deleteById(no);
    }

}
