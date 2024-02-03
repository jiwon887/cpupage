package com.jbnucpu.www.controller.member;

import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage/{id}")
    public String mypage(Model model, @PathVariable("id")Long id){

        if(this.myPageService.checkUser(id)){
            UserEntity user = this.myPageService.readUser(id);
            model.addAttribute("user", user);
            return "mypage";
        }else {

            return "redirect:/";
        }


    }
}
