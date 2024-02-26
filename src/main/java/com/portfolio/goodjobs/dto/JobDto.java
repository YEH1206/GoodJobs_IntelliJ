package com.portfolio.goodjobs.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

    private Long no;                    // 공고 번호

    @NotEmpty(message = "회원정보가 유효하지 않습니다.")
    @Size(max = 15, message = "회원정보가 유효하지 않습니다.")
    private String writer;              // 작성자 아이디

    @NotEmpty(message = "회사명이 유효하지 않습니다.")
    @Size(max = 30, message = "회사명이 유효하지 않습니다.")
    private String companyName;         // 회사명

    @NotEmpty(message = "회사 주소를 입력해주세요.")
    @Size(max = 100, message = "회사 주소는 100글자 이내로 작성해주세요.")
    private String address;             // 회사 주소

    @NotEmpty(message = "공고 제목을 입력해주세요.")
    @Size(max = 50, message = "공고 제목은 50자 이내로 작성해주세요.")
    private String title;               // 공고 제목

    private Timestamp regDate;          // 등록일

    @NotNull(message = "경력 구분을 선택해주세요.")
    private boolean exp;                // 경력구분 (T:경력, F:신입)

    @Min(value = 0, message = "경력기간은 0보다 작을 수 없습니다.")
    private Short expYear;          // 경력년수

    @NotNull(message = "최종학력을 선택해주세요.")
    private byte edu;                   // 최종학력 (0:무관, 1:고졸, 2:초대졸, 3:대졸, 4:석사, 5:박사)

    @Future(message="마감일은 현재 시간 이후로 설정해야 합니다.")
    @NotNull(message = "마감일을 설정해주세요.")
    private Instant deadline;              // 마감일 (epochMillis)

    public void setDeadline(Instant newDeadline) {
        this.deadline = newDeadline;
    }
}
