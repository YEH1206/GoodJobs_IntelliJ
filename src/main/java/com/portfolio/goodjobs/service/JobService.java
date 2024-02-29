package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.domain.JobLocation;
import com.portfolio.goodjobs.dto.JobDto;
import com.portfolio.goodjobs.dto.PageRequestDto;
import com.portfolio.goodjobs.dto.PageResponseDto;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public interface JobService extends TimeConverter {

    ZoneId zoneId = ZoneId.of("Asia/Tokyo");

    ModelMapper modelMapper();

    /**
     * 채용공고를 DB에 저장하고, 생성된 공고번호를 반환한다.
     */
    Long register(JobDto jobDto);

    /**
     * 공고번호로 채용공고를 조회한다.
     */
    JobDto readOne(Long no);

    /**
     * 공고번호에 해당되는 채용공고를 수정한다.
     */
    void modify(JobDto jobDto);

    /**
     * 공고번호에 해당되는 채용공고를 삭제한다.
     */
    void remove(Long no);

    /**
     * 검색조건에 해당하는 채용공고를 조회하고, 목록을 반환한다.
     */
    PageResponseDto<JobDto> list(PageRequestDto pageRequestDto);

    default Job dtoToEntity(JobDto jobDto) {

        Job job = modelMapper().map(jobDto, Job.class);

        // deadline: Instant -> LocalDateTime
        LocalDateTime deadline = instantToLocalDateTime(jobDto.getDeadline(), zoneId);

        // deadline을 '9999-12-31T23:59' 이내로 제한한다.
        LocalDateTime max = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
        job.setDeadline(deadline.isBefore(max) ? deadline : max);

        // 근무지역: List -> Set
        if (jobDto.getLocations() != null) {
            for (int jobLocation : jobDto.getLocations()) {
                job.addLocation(jobLocation);
            }
        }

        return job;
    }

    default JobDto entityToDto(Job job) {

        JobDto jobDto = modelMapper().map(job, JobDto.class);

        // deadline: LocalDateTime -> Instant
        Instant deadline = localDateTimeToInstant(job.getDeadline(), zoneId);
        jobDto.setDeadline(deadline);

        // regDate: LocalDateTime -> Instant
        Instant regDate = localDateTimeToInstant(job.getRegDate(), zoneId);
        jobDto.setRegDate(regDate);

        // modDate: LocalDateTime -> Instant
        Instant modDate = localDateTimeToInstant(job.getModDate(), zoneId);
        jobDto.setModDate(modDate);

        // 근무지역: Set -> List
        List<Integer> locationList = job.getLocationSet()
                        .stream()
                                .map(JobLocation::getSigungu)
                                        .sorted()
                                                .collect(Collectors.toList());
        jobDto.setLocations(locationList);

        return jobDto;
    }
}
