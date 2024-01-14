package com.jbnucpu.www.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class NoticeController {
    @GetMapping("/notice")
    public String notice() {

        return "notice";
    }

}
