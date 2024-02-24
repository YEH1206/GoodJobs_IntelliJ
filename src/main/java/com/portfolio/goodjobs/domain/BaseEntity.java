package com.portfolio.goodjobs.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
@Getter
public class BaseEntity {

    /*
    * 대부분의 테이블에 공통적으로 들어갈 칼럼을 모아서 관리한다.
    * AuditingEntityListener를 적용하면 엔티티가 DB에 추가되거나 변경될 때 자동으로 시간 값을 지정할 수 있다.
    * 이를 위해서는 프로젝트 설정에 @EnableJpaAuditing을 추가해주어야 한다.
    * */

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    private Timestamp regDate;

    @LastModifiedDate
    @Column(name = "mod_date")
    private Timestamp modDate;
}
