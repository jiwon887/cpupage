package com.jbnucpu.www.service.updatemember;

import com.jbnucpu.www.dto.CustomUserDetails;
import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void updateMember(String name, String newPassword, String newName, String newPhoneNumber, String newNickName){

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
    }

    public String reGenerateSession(@Valid @ModelAttribute HttpSession session,HttpServletRequest request) {

        session.invalidate();

        HttpSession newSession = request.getSession(true);

        CustomUserDetails userDetails =(CustomUserDetails) Objects.requireNonNull(session.getAttribute(SessionConst.LOGIN));

        newSession.setAttribute(SessionConst.LOGIN, userDetails);



        return "/";
    }

    public class SessionConst{
        public static final String LOGIN = "longin member";
    }


}
