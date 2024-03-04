package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.domain.Member;
import com.portfolio.goodjobs.domain.MemberRole;
import com.portfolio.goodjobs.dto.MemberDto;
import com.portfolio.goodjobs.exception.IdDuplicateException;
import com.portfolio.goodjobs.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> result = memberRepository.findById(username);

        return result.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public boolean checkDuplicateId(String id) {
        return memberRepository.existsById(id);
    }

    @Override
    public void join(MemberDto memberDto) throws IdDuplicateException {
        String id = memberDto.getId();

        boolean exist = memberRepository.existsById(id);

        if(exist) throw new IdDuplicateException("중복된 아이디입니다.");

        Member member = modelMapper.map(memberDto, Member.class);
        member.changePassword(passwordEncoder.encode(memberDto.getPw()));
        member.changeRole(MemberRole.PERSONAL);

        memberRepository.save(member);
    }
}
