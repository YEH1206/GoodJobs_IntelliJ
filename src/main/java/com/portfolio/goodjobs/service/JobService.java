package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.domain.Job;
import com.portfolio.goodjobs.domain.JobLocation;
import com.portfolio.goodjobs.dto.*;
import com.portfolio.goodjobs.enums.CodeEnum;
import com.portfolio.goodjobs.enums.sigungu.Gyeonggi;
import com.portfolio.goodjobs.enums.sigungu.Seoul;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    PageResponseDto<JobListDto> jobList(PageRequestDto pageRequestDto);

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


    /**
     * TODO: 동적 쿼리 활용할 때 select 범위 지정하는 거 공부해야 함.
     * select 범위 설정이 잘 안돼서 임시방편으로 JobListDto를 만들어 활용하고 있음.
     */
    default <D extends BaseDto> D entityToDto(Job job, Class<D> dtoClass) {
        D dto = modelMapper().map(job, dtoClass);

        // deadline: LocalDateTime -> Instant
        Instant deadline = localDateTimeToInstant(job.getDeadline(), zoneId);
        dto.setDeadline(deadline);

        // regDate: LocalDateTime -> Instant
        Instant regDate = localDateTimeToInstant(job.getRegDate(), zoneId);
        dto.setRegDate(regDate);

        // 근무지역: Set<JobLocation> -> List<String>
        List<String> locationList = job.getLocationSet()
                .stream()
                .map(JobLocation::getSigungu)
                .sorted()
                .map(code -> getNameByCode(code, determineEnumClass(code)))
                .toList();
        dto.setSigunguNames(locationList);

        return dto;
    }

//    default JobDto entityToDto(Job job) {
//        System.out.println(job);
//        System.out.println(job.getLocationSet());
//        System.out.println(job.getRegDate());
//
//        JobDto jobDto = modelMapper().map(job, JobDto.class);
//
//        // deadline: LocalDateTime -> Instant
//        Instant deadline = localDateTimeToInstant(job.getDeadline(), zoneId);
//        jobDto.setDeadline(deadline);
//
//        // regDate: LocalDateTime -> Instant
//        Instant regDate = localDateTimeToInstant(job.getRegDate(), zoneId);
//        jobDto.setRegDate(regDate);
//
//        // 근무지역: Set<JobLocation> -> List<String>
//        List<String> locationList = job.getLocationSet()
//                .stream()
//                .map(JobLocation::getSigungu)
//                .sorted()
//                .map(code -> getNameByCode(code, determineEnumClass(code)))
//                .toList();
//        jobDto.setSigunguNames(locationList);
//
//        return jobDto;
//    }

    // 시군구 코드 -> 시·도 선택
    default <E extends Enum<E> & CodeEnum> Class<? extends CodeEnum> determineEnumClass(int code) {
        if (String.valueOf(code).startsWith("11")) {
            return Seoul.class;

        } else if (String.valueOf(code).startsWith("41")) {
            return Gyeonggi.class;
        }
        return null;
    }

    // 시군구 코드 -> 시군구 이름
    default <E extends Enum<E> & CodeEnum> String getNameByCode(int code, Class<? extends CodeEnum> enumClass) {
        if (enumClass != null) {

            Optional<? extends CodeEnum> matchingEnum = Arrays.stream(enumClass.getEnumConstants())
                    .filter(e -> e.getCode() == code)
                    .findFirst();

            return matchingEnum.map(CodeEnum::getName).orElse("");
        }
        return "";
    }
}
