package com.jbnucpu.www.controller;

import com.jbnucpu.www.dto.CustomUserDetails;
import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.UserRepository;
import com.jbnucpu.www.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserRepository userRepository;

    private final AuthService authService;

    @GetMapping("/")
    public String mainPage(){

        // 테스트 때문에 썼습니다 나중에 지우겠습니다 - 도현
        if(authService.isAuthenticated()){
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

            String studentNumber = SecurityContextHolder.getContext().getAuthentication().getName();

            UserEntity loginUser = userRepository.findByStudentnumber(studentNumber);

            System.out.println(loginUser.getName());

            return "main";
        }


        return "main";
    }
}
