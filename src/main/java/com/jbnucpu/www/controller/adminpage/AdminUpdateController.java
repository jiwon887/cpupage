package com.jbnucpu.www.controller.adminpage;

import com.jbnucpu.www.dto.UserInfoDTO;
import com.jbnucpu.www.service.AdminUpdateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminUpdateController {

    private final AdminUpdateService adminUpdateService;

    public AdminUpdateController(AdminUpdateService adminUpdateService){
        this.adminUpdateService = adminUpdateService;
    }

    @GetMapping("/admin_update")
    public String adminUpdateP(@RequestParam("studentNumber")String studentNumber, Model model){
        model.addAttribute("studentNumber", studentNumber);
        return "admin_update";
    }

    @PostMapping("/admin_update")
    public String updateMemberInfo(@RequestParam("newPassword") String newPassword,
                                   @RequestParam("studentNumber") String studentNumber,
                                   @RequestParam("newName") String newName,
                                   @RequestParam("newPhoneNumber") String newPhoneNumber,
                                   @RequestParam("newNickname") String newNickname,
                                   @RequestParam("newRole") String newRole) {


        adminUpdateService.adminUpdateMember(studentNumber, newPassword, newName, newPhoneNumber, newNickname, newRole);


        return "redirect:/admin_main";
    }

}
