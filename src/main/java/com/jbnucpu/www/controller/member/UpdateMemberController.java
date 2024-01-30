package com.jbnucpu.www.controller.member;

import com.jbnucpu.www.dto.CustomUserDetails;
import com.jbnucpu.www.service.updatemember.UpdateMemberService;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


//실제 정보를 수정하는 페이지
@Controller
public class UpdateMemberController {

    @Autowired
    private UpdateMemberService updateMemberService;


    @GetMapping("/updatemember")
    public String updateMemberForm(){


        return "/updatemember";
    }


    @PostMapping("/updatemember")
    public String updateMemberInfo(@RequestParam("name") String name,
                                   @RequestParam("newPassword") String newPassword,
                                   @RequestParam("newName") String newName,
                                   @RequestParam("newPhoneNumber") String newPhoneNumber,
                                   @RequestParam("newNickname") String newNickname) {

        updateMemberService.updateMember(name, newPassword, newName, newPhoneNumber, newNickname);


        return "redirect:/";
    }
}
