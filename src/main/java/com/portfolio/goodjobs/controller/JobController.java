package com.portfolio.goodjobs.controller;

import com.portfolio.goodjobs.dto.JobDto;
import com.portfolio.goodjobs.dto.PageRequestDto;
import com.portfolio.goodjobs.enums.Sido;
import com.portfolio.goodjobs.enums.sigungu.Gyeonggi;
import com.portfolio.goodjobs.enums.sigungu.Seoul;
import com.portfolio.goodjobs.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/job")
@RequiredArgsConstructor
@Log4j2
public class JobController {

    private final JobService jobService;

    @GetMapping("/index")
    public String temp() {
        System.out.println("job index .......................");
        return "layout/index";
    }

    @GetMapping("/register")
    public void registerGET(Model model) {
        model.addAttribute("sidoList", Sido.values());
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


    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) {

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

    private static class SigunguDto {
        private final String name;

        private final int code;

        public SigunguDto(String name, int code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public int getCode() {
            return code;
        }
    }
}
