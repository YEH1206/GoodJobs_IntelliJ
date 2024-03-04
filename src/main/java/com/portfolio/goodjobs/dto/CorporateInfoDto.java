package com.portfolio.goodjobs.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorporateInfoDto {

    @NotEmpty(message = "아이디를 입력해주세요.")
    private String id;

    @AssertTrue(message = "아이디 중복 검사를 해주세요.")
    private boolean validId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String pw;

    @Email(message = "유효하지 않은 이메일입니다.")
    private String email;

    @NotEmpty(message = "회사명을 입력해주세요.")
    private String companyName;

    @NotEmpty(message = "사업자등록번호를 입력해주세요.")
    private Long regNum;

    @AssertTrue(message = "사업자 인증이 필요합니다.")
    private Boolean validRegNum;

    @NotEmpty(message = "대표자명을 입력해주세요.")
    private String ceo;

}
