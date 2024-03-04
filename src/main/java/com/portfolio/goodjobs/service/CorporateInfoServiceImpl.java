package com.portfolio.goodjobs.service;

import com.portfolio.goodjobs.domain.CorporateInfo;
import com.portfolio.goodjobs.domain.MemberRole;
import com.portfolio.goodjobs.dto.CorporateInfoDto;
import com.portfolio.goodjobs.exception.IdDuplicateException;
import com.portfolio.goodjobs.repository.CorporateInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CorporateInfoServiceImpl implements CorporateInfoService {

    private final ModelMapper modelMapper;

    private final CorporateInfoRepository corporateInfoRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void join(CorporateInfoDto corporateInfoDto) throws IdDuplicateException {

        String id = corporateInfoDto.getId();

        boolean exist = corporateInfoRepository.existsById(id);

        if(exist) throw new IdDuplicateException("중복된 아이디입니다.");

        CorporateInfo corporateInfo = modelMapper.map(corporateInfoDto, CorporateInfo.class);
        corporateInfo.changePassword(passwordEncoder.encode(corporateInfoDto.getPw()));
        corporateInfo.changeRole(MemberRole.CORPORATE);

        corporateInfoRepository.save(corporateInfo);
    }

    @Override
    public CorporateInfoDto getCorporateInfo(String id) {
        CorporateInfo corporateInfo = corporateInfoRepository.findById(id).orElseThrow();

        return modelMapper.map(corporateInfo, CorporateInfoDto.class);
    }

}
