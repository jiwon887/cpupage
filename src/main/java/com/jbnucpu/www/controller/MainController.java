package com.jbnucpu.www.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String mainPage(){

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        return "main";
    }
}
