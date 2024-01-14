package com.jbnucpu.www.controller;

import com.jbnucpu.www.service.StudyEnrollService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudyEnrollController {

    private final StudyEnrollService studyEnrollService;

    public StudyEnrollController(StudyEnrollService studyEnrollService){
        this.studyEnrollService = studyEnrollService;
    }

    // 개별 스터디 페이지
    @GetMapping("/study/{no}")
    public String studyNo(@PathVariable("no")Long no, Model model){

        model.addAttribute("data", studyEnrollService.processStudy(no).get());
        return "studyno";
    }

    // 스터디 신청
    @PostMapping("/study/{no}")
    public String enrollStudy(@PathVariable("no")Long no, Model model){

        return "studylist";
    }
}
