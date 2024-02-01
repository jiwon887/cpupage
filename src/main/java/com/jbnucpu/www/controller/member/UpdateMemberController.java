package com.jbnucpu.www.controller.member;

import com.jbnucpu.www.service.UpdateMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UpdateMemberController {

    @Autowired
    private UpdateMemberService updateMemberService;


    @GetMapping("/updatemember")
    public String updateMemberForm(){


        return "/updatemember";
    }


    @PostMapping("/updatemember")
    public String updateMemberInfo(@RequestParam("newPassword") String newPassword,
                                   @RequestParam("newName") String newName,
                                   @RequestParam("newPhoneNumber") String newPhoneNumber,
                                   @RequestParam("newNickname") String newNickname) {


        updateMemberService.updateMember(newPassword, newName, newPhoneNumber, newNickname);


        return "redirect:/";
    }

}
