package com.jbnucpu.www.service;

import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class DeleteMemberService {

    private final UserRepository userRepository;

    @Autowired
    public DeleteMemberService(UserRepository userRepository){
        this.userRepository =userRepository;
    }

    public String deleteMember(){

        try{
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userRepository.deleteByName(username);

            return "redirect:/";
        }
        catch(Exception e){

            return "/delete";
        }

    }

}
