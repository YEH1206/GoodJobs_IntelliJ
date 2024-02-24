package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.dto.JobDto;
import com.portfolio.goodjobs.dto.PageRequestDto;
import com.portfolio.goodjobs.dto.PageResponseDto;
import com.portfolio.goodjobs.repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{

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
        return null;
    }

    @Override
    public void modify(JobDto jobDto) {

    }

    @Override
    public void remove(Long no) {

    }

    @Override
    public PageResponseDto<JobDto> list(PageRequestDto pageRequestDto) {
        return null;
    }
}
