package com.jbnucpu.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EditorController {

    @GetMapping("/editor/noitce")
    public String noticeEditor(){

        return "editornotice";
    }

    @GetMapping("/editor/noitce/{no}")
    public String noticeUpdateEditor(@PathVariable("no")Long no){

        return "editornotice";
    }

    @GetMapping("/editor/content")
    public String contentEditor(){

        return "editorcontent";
    }

    @GetMapping("/editor/content/{no}")
    public String contentUpdateEditor(@PathVariable("no")Long no){

        return "editorcontent";
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
