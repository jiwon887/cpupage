package com.jbnucpu.www.controller.adminpage;

import com.jbnucpu.www.service.AdminUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminRoleButtonController {


    private final AdminUpdateService adminUpdateService;

    public AdminRoleButtonController(AdminUpdateService adminUpdateService){
        this.adminUpdateService = adminUpdateService;
    }

    @PostMapping("/updateUserRole")
    public String updateUserRole(@RequestParam("studentNumber")String studentNumber,
                                 @RequestParam("newRole")String newRole){
        adminUpdateService.updateUserRole(studentNumber, newRole);

        return "redirect:/admin_main";
    }

}
