package com.jbnucpu.www.controller.member;

import com.jbnucpu.www.entity.ContentEntity;
import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage/{id}")
    public String mypage(Model model, @PathVariable("id")Long id){

        if(this.myPageService.checkUser(id)){
            UserEntity user = this.myPageService.readUser(id);
            List<NoticeEntity> noticeList = this.myPageService.readUserNotice(id);
            List<ContentEntity> contentList = this.myPageService.readUserContent(id);
            model.addAttribute("user", user);
            model.addAttribute("noticeList", noticeList);
            model.addAttribute("contentList", contentList);
            return "mypage";
        }else {

            return "redirect:/";
        }


    }
}
