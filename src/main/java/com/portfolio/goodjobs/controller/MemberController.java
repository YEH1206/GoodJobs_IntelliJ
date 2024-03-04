package com.portfolio.goodjobs.controller;

import com.portfolio.goodjobs.dto.*;
import com.portfolio.goodjobs.exception.IdDuplicateException;
import com.portfolio.goodjobs.service.CorporateInfoService;
import com.portfolio.goodjobs.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final CorporateInfoService corporateInfoService;

    @GetMapping("/login")
    public String loginGet(PageRequestDto pageRequestDto, Model model, String error) {
        model.addAttribute("requestDto", pageRequestDto);
        if(error != null) {
            model.addAttribute("error", "아이디와 비밀번호가 일치하지 않습니다.");
        }
        return "member/login";
    }

    @GetMapping("/checkId/{id}")
    public ResponseEntity<String> checkId(@PathVariable("id") String id) {
        boolean isDuplicate = memberService.checkDuplicateId(id);

        if(isDuplicate) return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 아이디입니다.");
        else return ResponseEntity.ok("사용 가능한 아이디입니다.");
    }

    @GetMapping("/join/personal")
    public void joinPersonalGET(PageRequestDto pageRequestDto, Model model) {
        model.addAttribute("requestDto", pageRequestDto);
    }

    @PostMapping("/join/personal")
    public String joinPersonalPOST(MemberDto memberDto, RedirectAttributes redirectAttributes) {

        try {
            memberService.join(memberDto);
        } catch (IdDuplicateException e) {
            redirectAttributes.addFlashAttribute("error", "id");
            return "redirect:/member/join/personal";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }

    @GetMapping("/join/corporate")
    public void joinCorporateGET(PageRequestDto pageRequestDto, Model model) {
        model.addAttribute("requestDto", pageRequestDto);
    }

    @PostMapping("/join/corporate")
    public String joinCorporatePOST(CorporateInfoDto corporateInfoDto, RedirectAttributes redirectAttributes) {
        try {
            corporateInfoService.join(corporateInfoDto);
        } catch (IdDuplicateException e) {
            redirectAttributes.addFlashAttribute("error", "id");
            return "redirect:/member/join/corporate";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }
}
