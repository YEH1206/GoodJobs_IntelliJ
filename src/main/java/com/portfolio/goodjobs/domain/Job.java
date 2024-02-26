package com.portfolio.goodjobs.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Job extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;                    // 공고 번호

    @Column(length = 15, nullable = false)
    private String writer;              // 작성자 아이디

    @Column(length = 30, nullable = false)
    private String companyName;         // 회사명

    @Column(length = 100, nullable = false)
    private String address;             // 회사 주소

    @Column(length = 50, nullable = false)
    private String title;               // 공고 제목

    @Column(nullable = false)
    private boolean exp;                // 경력구분 (T:경력, F:신입)

    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Short expYear;              // 경력년수

    @Column(columnDefinition = "TINYINT")
    private byte edu;                   // 최종학력 (0:무관, 1:고졸, 2:초대졸, 3:대졸, 4:석사, 5:박사)

    @Column(nullable = false)
    private LocalDateTime deadline;     // 마감일 (시간대: Asia/Tokyo)

    public void setDeadline(LocalDateTime newDeadline) {
        this.deadline = newDeadline;
    }
}
