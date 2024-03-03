package com.portfolio.goodjobs.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "locationSet")
public class Job extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;                    // 공고 번호

    @Column(length = 15, nullable = false)
    private String writer;              // 작성자 아이디

    private String logoFileName;        // 회사 로고 파일명

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

    @Column(columnDefinition = "TINYINT", nullable = false)
    private byte edu;                   // 최종학력 (0:무관, 1:고졸, 2:초대졸, 3:대졸, 4:석사, 5:박사)

    @Column(nullable = false)
    private LocalDateTime deadline;     // 마감일 (시간대: Asia/Tokyo)

    @OneToMany(mappedBy = "job", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<JobLocation> locationSet = new HashSet<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String detail;              // 상세 내용

    public void change(String logoFileName, String address, String title,
                       boolean exp, Short expYear, byte edu, String detail) {
        this.logoFileName = logoFileName;
        this.address = address;
        this.title = title;
        this.exp = exp;
        this.expYear = expYear;
        this.edu = edu;
        this.detail = detail;
    }

    public void setDeadline(LocalDateTime newDeadline) {
        this.deadline = newDeadline;
    }

    public void addLocation(int sigungu) {
        JobLocation jobLocation = JobLocation.builder()
                .sigungu(sigungu)
                .ord(locationSet.size())
                .job(this)
                .build();
        locationSet.add(jobLocation);
    }

    public void clearLocations() {
        locationSet.forEach(jobLocation -> jobLocation.changeJob(null));
        this.locationSet.clear();
    }
}
