package com.jbnucpu.www.service;

import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    private final AuthService authService;

    //로그인 유저와 접속하려는 마이페이지와 일치하는 지 체크
    public Boolean checkUser(Long id){
        if(!authService.isAuthenticated()){
            System.out.println("접근 실패: 로그인 한 사용자만 접근 가능");
            return false;
        }

        UserEntity user = userRepository.findById(id).orElseThrow(()->new NoSuchElementException("해당 id에 대한 유저를 찾을 수 없습니다."));

        if(!authService.getUsername().equals(user.getStudentnumber())){
            System.out.println("접근 실패: 자신의 마이페이지만 접근 가능");
            return false;
        }

        return true;
    }

    // 기본 정보 불러오기
    public UserEntity readUser(Long id){

        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException(); // 해당 공지글이 존재하지 않을 경우
        }
    }
}
