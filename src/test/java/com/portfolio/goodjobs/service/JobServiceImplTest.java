package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.dto.JobDto;
import com.portfolio.goodjobs.dto.JobListDto;
import com.portfolio.goodjobs.dto.PageRequestDto;
import com.portfolio.goodjobs.dto.PageResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Min;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
                .closed(true)
                .build();

        PageResponseDto<JobListDto> result = jobService.jobList(pageRequestDto);
        log.info(result);
    }

    /**
     * 목록 조회를 위한 dummy 데이터 생성
     */
    @Test
    public void testInsert() {

        // 회사명 리스트
        List<String> companyNameList = List.of("(주)케어비지트", "(주)엠굿", "(주)체스트넛", "(주)혁산정보시스템",
                "(주)삼주전자", "(주)러닝팩토리", "(주)두올", "(주)동원테크", "(주)LX인터내셔널", "(주)인텔리안테크");

        // 제목 리스트
        List<String> titleList = List.of("2024 상반기 채용", "IT 기술리더 영입", "웹개발자 채용", "Java/Spring 개발자 모집 ",
                "IT 컨설턴트&기획자 모집", "금융권 운영 웹개발자 구인", "웹개발자 채용 - 웹풀스택" , "웹/앱 풀스택 개발자 모집",
                "Php 개발자 채용합니다.", "Back-End JAVA 웹 개발자 사원 채용", "웹개발 채용 - 주니어급",
                "프론트엔드 웹개발자 React.js 모십니다" , "IT B2B 기술영업/세일즈");

        // 시군구 리스트
        List<Integer> sigunguList = List.of(41820, 41280, 41281, 41285, 41287, 41290, 41210, 41610,
                41310, 41410, 41570, 41360, 41250, 41194, 41196, 41192, 41135, 41131, 41133, 41113, 41117,
                41111, 41115, 11680, 11740, 11305, 11500, 11620, 11215, 11530, 11545, 11350, 11320, 11230,
                11590, 11440, 11410, 11650, 11200, 11290, 11710, 11470, 11560, 11170, 11380, 11110, 11140, 11260);

        List<Boolean> booleans = List.of(true, false);


        for (int i = 0; i < 100; i++) {
            /**
             * 등록일과 마감일을 생성한다.
             */
            // 기한 설정 (2024-01-01 ~ 2024-03-31)
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            LocalDate endDate = LocalDate.of(2024, 3, 31);

            // 특정 날짜 생성
            LocalDate randomDate = generateRandomDate(startDate, endDate);

            // 특정 날짜의 자정 시각 Instant 생성
            Instant startOfDay = randomDate.atStartOfDay().toInstant(ZoneOffset.UTC);

            // 등록일 Instant 생성
            Instant regDate = generateRandomInstant(startOfDay, startOfDay.plusSeconds(24 * 60 * 60 - 1));

            // 마감일 Instant 생성
            Instant deadline = generateRandomInstant(regDate, startOfDay.plus(Duration.ofDays(generateRandomDays())));


            /**
             * 최대 10개의 근무지역을 추출한다.
             */
            Short start = (short) getRandomIndex(sigunguList.size() - 1);
            Short num = (short) getRandomIndex(10);
            Short end = (short) Math.min(start + num + 1, sigunguList.size());

            List<Integer> randomLocations = sigunguList.subList(start, end);

            /**
             * 100개의 JobDto를 생성하고 저장한다.
             */
            JobDto jobDto = JobDto.builder()
                    .writer("corporateID" + i)
                    .companyName(companyNameList.get(getRandomIndex(companyNameList.size())))
                    .address("경기도 화성시 동탄순환대로 21길 53, 1314동 2101호")
                    .title(titleList.get(getRandomIndex(titleList.size())))
                    .exp(booleans.get(getRandomIndex(2)))
                    .expYear(getRandomIndex(20))
                    .edu((byte) getRandomIndex(6))
                    .regDate(regDate)
                    .deadline(deadline)
                    .locations(randomLocations)
                    .detail("상세내용")
                    .build();

            jobService.register(jobDto);
        }

    }

    private static LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        return LocalDate.ofEpochDay(randomDay);
    }

    private static Instant generateRandomInstant(Instant start, Instant end) {
        long startEpochSecond = start.getEpochSecond();
        long endEpochSecond = end.getEpochSecond();
        long randomSecond = ThreadLocalRandom.current().nextLong(startEpochSecond, endEpochSecond + 1);
        return Instant.ofEpochSecond(randomSecond);
    }

    private static int generateRandomDays() {
        return ThreadLocalRandom.current().nextInt(1, 31);
    }

    private static @Min(value = 0, message = "경력기간은 0보다 작을 수 없습니다.") short getRandomIndex(int size) {
        return (byte) ThreadLocalRandom.current().nextInt(size);
    }
}