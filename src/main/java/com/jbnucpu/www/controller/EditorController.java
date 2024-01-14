package com.jbnucpu.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EditorController {

    @GetMapping("/editor/notice")
    public String noticeEditor(){

        return "editor";
    }

    @GetMapping("/editor/notice/{no}")
    public String noticeUpdateEditor(@PathVariable("no")Long no){

        return "editor";
    }

    @GetMapping("/editor/content")
    public String contentEditor(){

        return "editor";
    }

    @GetMapping("/editor/content/{no}")
    public String contentUpdateEditor(@PathVariable("no")Long no){

        return "editor";
    }

    @GetMapping("/editor/study")
    public String studyEditor(){

        return "editorstudy";
    }

    @GetMapping("/editor/study/{no}")
    public String studyUpdateEditor(@PathVariable("no")Long no){

        return "editorstudy";
    }
}
