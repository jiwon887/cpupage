package com.jbnucpu.www.controller.member;

import com.jbnucpu.www.service.DeleteMemberService;
import com.jbnucpu.www.service.updatemember.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteMemberController {

    @Autowired
    CheckService checkService;

    @PostMapping("/delete")
    public String checkPassword(@RequestParam String password){


        try{
            boolean passwordCorrect = checkService.checkPassword(password);

            if(passwordCorrect){
                return "redirect:/delete";
            }
            else{
                return "check";
            }
        }
        catch (Exception e){

            return "check";
        }
    }
}
