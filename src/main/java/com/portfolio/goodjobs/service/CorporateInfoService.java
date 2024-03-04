package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.dto.CorporateInfoDto;
import com.portfolio.goodjobs.exception.IdDuplicateException;

public interface CorporateInfoService {

    void join(CorporateInfoDto corporateInfoDto) throws IdDuplicateException;

    CorporateInfoDto getCorporateInfo(String id);

}
