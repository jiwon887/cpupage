package com.jbnucpu.www.service.updatemember;

import com.jbnucpu.www.dto.CustomUserDetails;
import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.lang.reflect.Member;
import java.util.Objects;

@Service
public class UpdateMemberService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByName(name);

        if(user == null){
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
        }

        return new CustomUserDetails(user);
    }
    @Transactional
    public String updateMember(String name, String newPassword, String newName, String newPhoneNumber, String newNickName){

        UserEntity user = userRepository.findByName(name);

        if (user == null) {
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.");
        }

        // 수정하려는 정보가 null이 아니면 업데이트
        if (newPassword != null) {
            user.setPassword(newPassword);
        }
        if (newName != null) {
            user.setName(newName);
        }
        if (newPhoneNumber != null) {
            user.setPhonenumber(newPhoneNumber);
        }
        if (newNickName != null) {
            user.setNickname(newNickName);
        }

        userRepository.save(user);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken  = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return ("/");
    }



}
