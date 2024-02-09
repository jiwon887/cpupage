package com.jbnucpu.www.controller.adminpage;

import com.jbnucpu.www.dto.StudyDTO;
import com.jbnucpu.www.dto.UserInfoDTO;
import com.jbnucpu.www.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminPageController {

    private final AdminService adminService;

    public AdminPageController(AdminService adminService){

        this.adminService = adminService;
    }

    @GetMapping("/admin_main")
    public String adminMainP(){

        return "admin_main";
    }

    @GetMapping("/admin_userlist")
    public String adminUserP(Model model){
        List<UserInfoDTO> userList = adminService.getAllMembers();
        model.addAttribute("allUser",userList);

        return "admin_user";
    }

    @GetMapping("/admin_studylist")
    public String adminStudyP(Model model){
        List<StudyDTO> studyList = adminService.getAllStudy();
        model.addAttribute("allStudy",studyList);

        return "admin_study";
    }


}
