package com.jbnucpu.www.controller;

import com.jbnucpu.www.dto.SignUpDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SignUpController {
    @GetMapping("/signup")
    public String signup(){

        return "signup";
    }

}
