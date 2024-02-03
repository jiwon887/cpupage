package com.jbnucpu.www.service;

import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    //로그인 아이디 (학번) 가져오기
    public String getUsername(){

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // 로그인한 유저의 UserEntity id값 가져오기

    public Long getUserId(){
        String studentNumber = this.getUsername();
        UserEntity user = userRepository.findByStudentnumber(studentNumber);
        return user.getId();
    }
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

}
