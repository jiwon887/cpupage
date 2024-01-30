package com.jbnucpu.www.service.updatemember;

import com.jbnucpu.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CheckService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean checkPassword(String enteredPassword) {

        try{String username = SecurityContextHolder.getContext().getAuthentication().getName();

            String actualPassword = userRepository.findByName(username).getPassword();

            return  bCryptPasswordEncoder.matches(enteredPassword, actualPassword);
        }
        catch (NullPointerException e) {
            System.out.println("틀림ㅋㅋ");
            return false;
        }
    }
}
