package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.dto.MemberDto;
import com.portfolio.goodjobs.exception.IdDuplicateException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    boolean checkDuplicateId(String id);

    void join(MemberDto memberDto) throws IdDuplicateException;
}
