package com.portfolio.goodjobs.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "job")
public class JobLocation implements Comparable<JobLocation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private int sigungu;        // 시군구 코드

    private int ord;

    @ManyToOne
    private Job job;

    @Override
    public int compareTo(JobLocation other) {
        return this.ord - other.ord;
    }

    public void changeJob(Job job) {
        this.job = job;
    }

}
