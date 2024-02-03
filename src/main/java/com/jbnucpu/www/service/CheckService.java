package com.jbnucpu.www.service;

import com.jbnucpu.www.dto.CustomUserDetails;
import com.jbnucpu.www.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public ResponseEntity<Boolean> checkId(String studentNumber) {
        //System.out.println(studentNumber.);
        if (userRepository.existsByStudentnumber(studentNumber)) {
            System.out.println("통과!");
            return ResponseEntity.ok(true);
        } else {
            System.out.println("이미 가입된 학번이에용");
            return ResponseEntity.ok(false);
        }
    }

    public boolean checkPassword(String enteredPassword) {

        try{
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String actualPassword = ((CustomUserDetails) principal).getPassword();

            System.out.println(actualPassword);

            return  bCryptPasswordEncoder.matches(enteredPassword, actualPassword);
        }
        catch (NullPointerException e) {
            System.out.println("틀림ㅋㅋ");
            return false;
        }
    }
}
