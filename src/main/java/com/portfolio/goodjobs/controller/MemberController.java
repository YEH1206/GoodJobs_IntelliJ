package com.portfolio.goodjobs.controller;

import com.portfolio.goodjobs.dto.*;
import com.portfolio.goodjobs.exception.IdDuplicateException;
import com.portfolio.goodjobs.service.CorporateInfoService;
import com.portfolio.goodjobs.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public void joinPersonalGET(PageRequestDto pageRequestDto,
                                @ModelAttribute MemberDto memberDto, Model model) {
        model.addAttribute("memberDto", memberDto);
        model.addAttribute("requestDto", pageRequestDto);
    }

    @PostMapping("/join/personal")
    public String joinPersonalPOST(@ModelAttribute @Valid MemberDto memberDto,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("memberDto", memberDto);
            return "redirect:/member/join/personal";
        }

        try {
            memberService.join(memberDto);
        } catch (IdDuplicateException e) {
            redirectAttributes.addFlashAttribute("error", "id");
            redirectAttributes.addFlashAttribute("memberDto", memberDto);
            return "redirect:/member/join/personal";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }

    @GetMapping("/join/corporate")
    public void joinCorporateGET(PageRequestDto pageRequestDto,
                                 @ModelAttribute CorporateInfoDto corporateInfoDto, Model model) {
        model.addAttribute("corporateInfoDto", corporateInfoDto);
        model.addAttribute("requestDto", pageRequestDto);
    }

    @PostMapping("/join/corporate")
    public String joinCorporatePOST(@ModelAttribute @Valid CorporateInfoDto corporateInfoDto,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("corporateInfoDto", corporateInfoDto);
            return "redirect:/member/join/corporate";
        }

        try {
            corporateInfoService.join(corporateInfoDto);
        } catch (IdDuplicateException e) {
            redirectAttributes.addFlashAttribute("error", "id");
            redirectAttributes.addFlashAttribute("corporateInfoDto", corporateInfoDto);
            return "redirect:/member/join/corporate";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }
}
