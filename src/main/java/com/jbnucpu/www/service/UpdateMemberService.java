package com.jbnucpu.www.service;

import com.jbnucpu.www.dto.CustomUserDetails;
import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UpdateMemberService {

    static public class ExistNickNameError extends RuntimeException {

        public ExistNickNameError(String message) {
            super(message);
        }
    }

    @Autowired
    UserRepository userRepository;

    @Transactional
    public String updateMember(String newPassword, String newName, String newPhoneNumber, String newNickName){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String studentNumber = ((CustomUserDetails) principal).getUsername(); // 학번 가져오기
        UserEntity user = userRepository.findByStudentnumber(studentNumber);

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
            //닉네임 중복 검사
            if(userRepository.existsByNickname(newNickName)){
                throw new ExistNickNameError("이미 사용 중인 닉네임입니다. 창의력을 발휘하세요.");
            }
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
