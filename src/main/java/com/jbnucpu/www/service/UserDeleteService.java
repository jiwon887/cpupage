package com.jbnucpu.www.service;


import com.jbnucpu.www.dto.CustomUserDetails;
import com.jbnucpu.www.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class UserDeleteService {

    private UserRepository userRepository;

    public UserDeleteService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public void fuckOff(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String studentNumber = ((CustomUserDetails) principal).getUsername();

        userRepository.deleteByStudentnumber(studentNumber);

    }

    public void fuckOffSession(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession();

        if(session!=null){
            session.invalidate();
        }

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());

    }

}
