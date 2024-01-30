package com.jbnucpu.www.controller.member;

import com.jbnucpu.www.service.updatemember.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


// 회원정보 수정 이전에 비밀번호로 인증하는 페이지
@Controller
public class CheckController {

    @Autowired
    private CheckService checkService;


    // check페이지에서 비밀번호 확인
    @GetMapping("/check")
    public String check(){

        return "check";
    }

    @PostMapping("/check")
    public String checkPassword(@RequestParam String password, Model model){
        boolean passwordCorrect = checkService.checkPassword(password);

        if(passwordCorrect){
            return "redirect:/update";
        }
        else{
            return "check";
        }
    }
}
