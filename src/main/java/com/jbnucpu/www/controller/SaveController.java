package com.jbnucpu.www.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SaveController {

    @PostMapping("/save/{type}")
    public String saveArticle(@PathVariable("type")String type) {


        return type;
    }
}