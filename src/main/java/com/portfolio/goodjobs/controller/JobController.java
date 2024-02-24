package com.portfolio.goodjobs.controller;

import com.portfolio.goodjobs.dto.JobDto;
import com.portfolio.goodjobs.dto.PageRequestDto;
import com.portfolio.goodjobs.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/list")
    public void list(PageRequestDto pageRequestDto, Model model) {

    }

    @GetMapping("/register")
    public void registerGET(Model model) {

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

        return "redirect:/job/index";
    }
}
