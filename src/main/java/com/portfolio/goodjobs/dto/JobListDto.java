package com.portfolio.goodjobs.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobListDto {

    private Long no;                    // 공고 번호

    private String logoFileName;        // 회사 로고 파일명

    private String companyName;         // 회사명

    private String title;               // 공고 제목

    private Instant regDate;            // 등록일

    private Instant modDate;            // 수정일

    private boolean exp;                // 경력구분 (T:경력, F:신입)

    private Short expYear;              // 경력년수

    private byte edu;                   // 최종학력 (0:무관, 1:고졸, 2:초대졸, 3:대졸, 4:석사, 5:박사)

    private Instant deadline;              // 마감일 (epochMillis)

}