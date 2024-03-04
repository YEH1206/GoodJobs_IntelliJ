package com.portfolio.goodjobs.repository;

import com.portfolio.goodjobs.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}

