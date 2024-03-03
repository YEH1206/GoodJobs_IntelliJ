package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.dto.JobDto;
import com.portfolio.goodjobs.dto.JobListDto;
import com.portfolio.goodjobs.dto.PageRequestDto;
import com.portfolio.goodjobs.dto.PageResponseDto;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
@Log4j2
class JobServiceImplTest {

    @Autowired
    JobService jobService;

    @Test
    @Transactional
    public void testReadOne() {
        JobDto jobDto = jobService.readOne(1L);
        log.info(jobDto.toString());
    }

    @Test
    public void testList() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .location("11530+11680")
                .closed(false)
                .build();

        PageResponseDto<JobListDto> result = jobService.jobList(pageRequestDto);
        log.info(result);
    }
}