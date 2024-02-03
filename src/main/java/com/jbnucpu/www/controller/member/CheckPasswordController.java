package com.jbnucpu.www.controller.member;

import com.jbnucpu.www.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


// 회원정보 수정 이전에 비밀번호로 인증하는 페이지
@Controller
public class CheckPasswordController {

    @Autowired
    private CheckService checkService;

    // 비밀번호 체크 페이지
    @GetMapping("/check")
    public String checkPasswordPage(){

        return "check";
    }

    // check페이지에서 비밀번호 확인
    //TODO: 로그인 안했을 때 버튼 클릭으로 post 요청 보내면 에러 발생
    @PostMapping("/check")
    public String checkPassword(@RequestParam String password){

        try{
            boolean passwordCorrect = checkService.checkPassword(password);

            if(passwordCorrect){
                System.out.println("비밀번호 체크 성공");
                return "updatemember";
            }
            else{
                System.out.println("비밀번호 틀렸는디?");
                return "check";
            }
        }
        catch (Exception e){

            return "check";
        }
    }
}
