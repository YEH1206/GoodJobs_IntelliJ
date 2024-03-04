package com.portfolio.goodjobs.controller;

import com.portfolio.goodjobs.dto.*;
import com.portfolio.goodjobs.enums.Sido;
import com.portfolio.goodjobs.enums.sigungu.Gyeonggi;
import com.portfolio.goodjobs.enums.sigungu.Seoul;
import com.portfolio.goodjobs.service.CorporateInfoService;
import com.portfolio.goodjobs.service.JobService;
import com.portfolio.goodjobs.service.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/job")
@RequiredArgsConstructor
@Log4j2
public class JobController {

    private final CorporateInfoService corporateInfoService;

    private final JobService jobService;

    @GetMapping("/register")
    public void registerGET(PageRequestDto pageRequestDto, Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        CorporateInfoDto corporateInfoDto = corporateInfoService.getCorporateInfo(id);

        model.addAttribute("id", corporateInfoDto.getId());
        model.addAttribute("companyName", corporateInfoDto.getCompanyName());
        model.addAttribute("sidoList", Sido.values());
        model.addAttribute("requestDto", pageRequestDto);
    }

    @PostMapping("/register")
    public String registerPOST(@Valid JobDto jobDto, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info(jobDto.toString());

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/job/register";
        }

        Long no = jobService.register(jobDto);

        redirectAttributes.addFlashAttribute("result", no);

        return "redirect:/job/register";
    }

    @GetMapping("read")
    public String readOne(Long no, PageRequestDto pageRequestDto, Model model) {
        JobDto jobDto = jobService.readOne(no);
        System.out.println(jobDto);
        model.addAttribute("dto", jobDto);
        return null;
    }

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) {
        PageResponseDto<JobListDto> responseDto = jobService.jobList(pageRequestDto);
        log.info("list test......");
        log.info(pageRequestDto);
        log.info(responseDto.toString());
        model.addAttribute("sidoList", Sido.values());
        model.addAttribute("requestDto", pageRequestDto);
        model.addAttribute("responseDto", responseDto);
    }

    @GetMapping("/searchResult")
    public void search(PageRequestDto pageRequestDto, Model model) {
        System.out.println(pageRequestDto);
        PageResponseDto<JobListDto> responseDto = jobService.jobList(pageRequestDto);
        model.addAttribute("sidoList", Sido.values());
        model.addAttribute("requestDto", pageRequestDto);
        model.addAttribute("responseDto", responseDto);
    }

    @GetMapping("/sigunguList")
    public ResponseEntity<List<SigunguDto>> sigunguList(@RequestParam String sidoName) {

        List<SigunguDto> sigunguList = new ArrayList<>();

        if("서울".equals(sidoName)) {
            for (Seoul seoul : Seoul.values()) {
                sigunguList.add(new SigunguDto(seoul.getName(), seoul.getCode()));
            }
            return ResponseEntity.ok(sigunguList);

        } else if("경기".equals(sidoName)) {
            for (Gyeonggi gyeonggi : Gyeonggi.values()) {
                sigunguList.add(new SigunguDto(gyeonggi.getName(), gyeonggi.getCode()));
            }
            return ResponseEntity.ok(sigunguList);

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Getter
    private static class SigunguDto {
        private final String name;

        private final int code;

        public SigunguDto(String name, int code) {
            this.name = name;
            this.code = code;
        }
    }
}
