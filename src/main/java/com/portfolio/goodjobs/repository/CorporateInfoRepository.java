package com.portfolio.goodjobs.repository;

import com.portfolio.goodjobs.domain.CorporateInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorporateInfoRepository extends JpaRepository<CorporateInfo, String> {
}
