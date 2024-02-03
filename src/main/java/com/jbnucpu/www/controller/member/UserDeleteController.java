package com.jbnucpu.www.controller.member;

import com.jbnucpu.www.service.CheckService;
import com.jbnucpu.www.service.UserDeleteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserDeleteController {

    private final CheckService checkService;

    private final UserDeleteService userDeleteService;

    public UserDeleteController(CheckService checkService, UserDeleteService userDeleteService){
        this.checkService = checkService;
        this.userDeleteService = userDeleteService;
    }


    @GetMapping("/quit")
    public String checkpasswordP(){

        return "quit";
    }
    @PostMapping("/quit")
    public String checkpassword(@RequestParam String password,HttpServletRequest request, HttpServletResponse response){

        boolean checkpassword = checkService.checkPassword(password);


        if(checkpassword){

            userDeleteService.fuckOff();
            userDeleteService.fuckOffSession(request, response);

            System.out.println("다신보지말자 배신자");

            return "redirect:/";
        }
        else{
            System.out.println("틀림ㅋㅋ");
            return "/quit";
        }
    }
}
