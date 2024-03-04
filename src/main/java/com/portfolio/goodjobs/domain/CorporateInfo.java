package com.portfolio.goodjobs.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CorporateInfo extends Member {

    // 기업회원으로 가입할 때 필요한 개인정보
    private String companyName;        // 회사명
    private Long regNum;               // 사업자등록번호
    private String ceo;                // 대표자명

}
