package com.portfolio.goodjobs.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[a-z0-9_]{5,15}$",
            message = "아이디는 알파벳 소문자, " +
                    "숫자, '_'만 쓸 수 있습니다. (5~15글자)")
    private String id;

    @AssertTrue(message = "아이디 중복 검사를 해주세요.")
    private boolean validId;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)" +
            "(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,16}$",
            message = "비밀번호는 영문자, 숫자, " +
                    "특수문자(!@#$%^&*)를 포함해야 합니다. (8~16글자)")
    private String pw;

    @Email(message = "올바르지 않은 이메일입니다.")
    private String email;

}
