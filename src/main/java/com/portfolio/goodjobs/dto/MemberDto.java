package com.portfolio.goodjobs.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@ToString
public class MemberDto {

    @NotEmpty(message = "아이디를 입력해주세요.")
    private String id;

    @AssertTrue(message = "아이디 중복 검사를 해주세요.")
    private boolean validId;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String pw;

    @Email(message = "올바르지 않은 이메일입니다.")
    private String email;
}
